package ru.practicum.ewm.service.compilation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.model.category.CategoryMapper;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.CompilationMapper;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.model.compilationevent.CompilationEvent;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestState;
import ru.practicum.ewm.service.event.EventClient;
import ru.practicum.ewm.storage.category.CategoryRepository;
import ru.practicum.ewm.storage.compilation.CompilationEventRepository;
import ru.practicum.ewm.storage.compilation.CompilationRepository;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.request.RequestRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс, имплементирующий интерфейс для работы сервиса подборок событий
 */
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final EventClient eventClient;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository,
                                  CompilationEventRepository compilationEventRepository, EventClient eventClient,
                                  RequestRepository requestRepository, CategoryRepository categoryRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.compilationEventRepository = compilationEventRepository;
        this.eventClient = eventClient;
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Метод для создания подборки событий
     */
    @Transactional
    @Override
    public CompilationDto create(NewCompilationDto newCompilation) {
        Compilation compilation = compilationRepository.save(CompilationMapper.newToCompilation(newCompilation));
        Set<Event> events = compilation.getEvents();
        for (Event event : events) {
            Event foundEvent = eventRepository.findById(event.getId()).orElseThrow();
            event.setTitle(foundEvent.getTitle());
            event.setAnnotation(foundEvent.getAnnotation());
            event.setDescription(foundEvent.getDescription());
            event.setLocation(foundEvent.getLocation());
            event.setEventDate(foundEvent.getEventDate());
            event.setCategoryId(foundEvent.getCategoryId());
            event.setPaid(foundEvent.getPaid());
            event.setParticipantLimit(foundEvent.getParticipantLimit());
            event.setRequestModeration(foundEvent.getRequestModeration());
            event.setInitiator(foundEvent.getInitiator());
            event.setEventState(foundEvent.getEventState());
        }
        CompilationDto compilationViews = CompilationMapper.toCompilationDto(compilation);
        for (EventDtoWithViews event : compilationViews.getEvents()) {
            event.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                    .findById(event.getCategory().getId()).orElseThrow()));
            long hits = getHits(event);
            event.setViews(hits);
            List<Request> confirmed = requestRepository.findByEventIdAndRequestState(event.getId(),
                    RequestState.ACCEPTED);
            event.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
        }
        return compilationViews;
    }

    /**
     * Метод для удаления подборки событий по ее id
     */
    @Transactional
    @Override
    public void deleteById(Long compId) {
        compilationRepository.deleteById(compId);
    }

    /**
     * Метод для удаления события из подборки событий по его id
     */
    @Transactional
    @Override
    public void deleteEventByIdFromCompilation(Long compId, Long eventId) {
        compilationEventRepository.delete(new CompilationEvent(compId, eventId));
    }


    /**
     * Метод для добавления события в подборку событий по его id
     */
    @Transactional
    @Override
    public void addEventByIdToCompilation(Long compId, Long eventId) {
        CompilationEvent compilationEvent = new CompilationEvent();
        compilationEvent.setEventId(eventId);
        compilationEvent.setCompilationId(compId);
        compilationEventRepository.save(compilationEvent);
    }

    /**
     * Метод для открепления подборки событий на главной странице
     */
    @Transactional
    @Override
    public void unpin(Long compId) {
        if (compilationRepository.findById(compId).isEmpty()) {
            throw new CompilationNotFoundException("There is no compilation with id = " + compId);
        } else {
            Compilation compilation = compilationRepository.findById(compId).get();
            compilation.setPinned(false);
            compilationRepository.save(compilation);
        }
    }

    /**
     * Метод для закрепления подборки событий на главной странице
     */
    @Transactional
    @Override
    public void pin(Long compId) {
        if (compilationRepository.findById(compId).isEmpty()) {
            throw new CompilationNotFoundException("There is no compilation with id = " + compId);
        } else {
            Compilation compilation = compilationRepository.findById(compId).get();
            compilation.setPinned(true);
            compilationRepository.save(compilation);
        }
    }

    /**
     * Метод для получения информации по подборке событий
     */
    @Transactional(readOnly = true)
    @Override
    public CompilationDto getById(Long compId) {
        if (compilationRepository.findById(compId).isEmpty()) {
            throw new CompilationNotFoundException("There is no compilation with id = " + compId);
        } else {
            CompilationDto compilationViews = CompilationMapper
                    .toCompilationDto(compilationRepository.findById(compId).get());
            for (EventDtoWithViews event : compilationViews.getEvents()) {
                event.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(event.getCategory().getId()).orElseThrow()));
                long hits = getHits(event);
                event.setViews(hits);
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(event.getId(),
                        RequestState.ACCEPTED);
                event.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
            }
            return compilationViews;
        }
    }

    /**
     * Метод для получения подборок событий по заданным фильтрам
     */
    @Transactional(readOnly = true)
    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        List<Compilation> foundList = compilationRepository.findByPinned(pinned, PageRequest.of(from / size, size));
        List<CompilationDto> compilationList = new ArrayList<>();
        for (Compilation compilation : foundList) {
            CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilation);
            for (EventDtoWithViews event : compilationDto.getEvents()) {
                event.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(event.getCategory().getId()).orElseThrow()));
                long hits = getHits(event);
                event.setViews(hits);
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(event.getId(),
                        RequestState.ACCEPTED);
                event.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
            }
            compilationList.add(compilationDto);
        }
        return compilationList;
    }

    /**
     * Метод для получения количества просмотров по событию
     */
    private long getHits(EventDtoWithViews event) {
        LocalDateTime minDate = eventRepository.findMinDate().getEventDate();
        LocalDateTime maxDate = eventRepository.findMaxDate().getEventDate();
        String uri = "/events/" + event.getId();
        ResponseEntity<Object> list = eventClient.get(minDate, maxDate, uri, false);
        @SuppressWarnings({"unchecked"})
        List<Map<String, Object>> listStat = (List<Map<String, Object>>) list.getBody();
        long hits = 0L;
        if (listStat != null && listStat.size() != 0) {
            Map<String, Object> el = listStat.get(0);
            hits = Long.parseLong(String.valueOf(el.get("hits")));
        }
        return hits;
    }
}
