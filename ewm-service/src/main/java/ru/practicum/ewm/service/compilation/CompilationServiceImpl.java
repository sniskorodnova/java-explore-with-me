package ru.practicum.ewm.service.compilation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.CompilationMapper;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.storage.compilation.CompilationRepository;
import ru.practicum.ewm.storage.event.EventRepository;

import java.util.Set;

@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

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
            event.setState(foundEvent.getState());
        }
        return CompilationMapper.toCompilationDto(compilation);
    }
}
