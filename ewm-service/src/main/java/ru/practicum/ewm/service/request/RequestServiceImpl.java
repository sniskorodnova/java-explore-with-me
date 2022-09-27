package ru.practicum.ewm.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventState;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestDto;
import ru.practicum.ewm.model.request.RequestMapper;
import ru.practicum.ewm.model.request.RequestState;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.request.RequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса заявок на участие в событиях
 */
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Метод для создания заявки на участие в событии. Нельзя добавить повторный запрос. Инициатор события не
     * может добавить запрос на участие в своем событии. Нельзя участвовать в неопубликованном событии. Нельзя
     * участвовать в событии, если у него достигнут лимит запросов на участие. Если для события отключена
     * пре-модерация запросов на участие, то запрос автоматически переходит в состояние подтвержденного.
     * Если лимит запросов в событии равен 0, то заявки автоматически переходят в подтвержденные
     */
    @Transactional
    @Override
    public RequestDto create(Long userId, Long eventId) {
        List<Request> checkIfAlreadyExists = requestRepository.findByUserIdAndEventIdAndRequestStateIsNot(userId,
                eventId, RequestState.CANCELLED);
        if (!checkIfAlreadyExists.isEmpty()) {
            throw new UserAlreadyHasRequestException("User with id = " + userId + " already has request for "
                    + "participation in event with id = " + eventId);
        } else {
            Event eventForUser = eventRepository.findByInitiatorIdAndId(userId, eventId);
            if (eventForUser != null) {
                throw new UserCantCreateRequestException("User can't create request for their own event");
            } else {
                if (eventRepository.findById(eventId).isEmpty()) {
                    throw new EventNotFoundException("There is no event with id = " + eventId);
                } else {
                    if (eventRepository.findById(eventId).get().getEventState() != EventState.PUBLISHED) {
                        throw new UserCantCreateRequestException("Event is not published");
                    } else {
                        if (eventRepository.findById(eventId).get().getParticipantLimit() != 0
                                && eventRepository.findById(eventId).get().getParticipantLimit()
                                == requestRepository.findByEventIdAndRequestStateIsNot(eventId,
                                RequestState.CANCELLED).size()) {
                            throw new UserCantCreateRequestException("Event is fully booked");
                        } else {
                            Request request = new Request();
                            request.setEventId(eventId);
                            request.setUserId(userId);
                            if (!eventRepository.findById(eventId).get().getRequestModeration()
                                    || eventRepository.findById(eventId).get().getParticipantLimit() == 0) {
                                request.setRequestState(RequestState.ACCEPTED);
                            } else {
                                request.setRequestState(RequestState.PENDING);
                            }
                            request.setCreated(LocalDateTime.now());
                            return RequestMapper.toRequestDto(requestRepository.save(request));
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод для отмены текущим пользователем своей заявки на участие в событии
     */
    @Transactional
    @Override
    public RequestDto cancel(Long userId, Long requestId) {
        if (requestRepository.findById(requestId).isEmpty()) {
            throw new RequestNotFoundException("There is no request with id = " + requestId);
        } else {
            if (!requestRepository.findById(requestId).get().getUserId().equals(userId)) {
                throw new RequestCantBeUpdatedException("Request with id = " + requestId + " doesn't belong "
                        + "to user with id = " + userId);
            } else {
                Request request = requestRepository.findById(requestId).get();
                request.setRequestState(RequestState.CANCELLED);
                return RequestMapper.toRequestDto(requestRepository.save(request));
            }
        }
    }

    /**
     * Метод для получения информации о заявках текущего пользователя на участие в чужих событиях
     */
    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getAllForUser(Long userId) {
        List<RequestDto> foundList = new ArrayList<>();
        List<Request> requests = requestRepository.findByUserId(userId);
        if (requests == null) {
            return null;
        } else {
            for (Request request : requests) {
                foundList.add(RequestMapper.toRequestDto(request));
            }
            return foundList;
        }
    }

    /**
     * Метод для получения информации о заявках на участие в событиях текущего пользователя
     */
    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getAllForUserEvents(Long userId, Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (!event.getInitiator().getId().equals(userId)) {
                throw new UserNotAllowedToViewEventException("Event with id = " + eventId + " doesn't belong to "
                        + "user with id = " + userId);
            } else {
                List<RequestDto> foundList = new ArrayList<>();
                List<Request> requests = requestRepository.findByEventId(userId);
                if (requests == null) {
                    return null;
                } else {
                    for (Request request : requests) {
                        foundList.add(RequestMapper.toRequestDto(request));
                    }
                    return foundList;
                }
            }
        }
    }

    /**
     * Метод для подтверждения чужой заявки на участие в событии текущего пользователя. Нельзя подтвердить
     * заявку, если уже достигнут лимит по заявкам на данное событие. Если при подтверждении данной заявки
     * лимит заявок для события исчерпан, то все неподтвержденные заявки отклоняются
     */
    @Transactional
    @Override
    public RequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (!event.getInitiator().getId().equals(userId)) {
                throw new UserNotAllowedToViewEventException("Event with id = " + eventId + " doesn't belong to "
                        + "user with id = " + userId);
            } else {
                if (requestRepository.findById(reqId).isEmpty()) {
                    throw new RequestNotFoundException("There is no request with id = " + reqId);
                } else {
                    if (!requestRepository.findById(reqId).get().getEventId().equals(eventId)) {
                        throw new RequestCantBeUpdatedException("Request with id = " + reqId + " was not sent "
                                + "for event with id = " + eventId);
                    } else {
                        if (eventRepository.findById(eventId).get().getParticipantLimit() != 0
                                && (eventRepository.findById(eventId).get().getParticipantLimit()
                                .equals(Long.parseLong(String.valueOf(requestRepository
                                        .findByEventIdAndRequestState(eventId, RequestState.ACCEPTED).size()))))) {
                            throw new RequestCantBeUpdatedException("Event is fully booked");
                        } else {
                            Request request = requestRepository.findById(reqId).get();
                            request.setRequestState(RequestState.ACCEPTED);
                            requestRepository.save(request);
                            if (eventRepository.findById(eventId).get().getParticipantLimit() != 0
                                    && (eventRepository.findById(eventId).get().getParticipantLimit()
                                    .equals(Long.parseLong(String.valueOf(requestRepository
                                            .findByEventIdAndRequestState(eventId, RequestState.ACCEPTED).size()))))) {
                                List<Request> pendingRequests = requestRepository
                                        .findByEventIdAndRequestState(eventId, RequestState.PENDING);
                                for (Request req : pendingRequests) {
                                    req.setRequestState(RequestState.REJECTED);
                                    requestRepository.save(req);
                                }
                            }
                            return RequestMapper.toRequestDto(request);
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод для отклонения чужой заявки на участие в событии текущего пользователя
     */
    @Transactional
    @Override
    public RequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (!event.getInitiator().getId().equals(userId)) {
                throw new UserNotAllowedToViewEventException("Event with id = " + eventId + " doesn't belong to "
                        + "user with id = " + userId);
            } else {
                if (requestRepository.findById(reqId).isEmpty()) {
                    throw new RequestNotFoundException("There is no request with id = " + reqId);
                } else {
                    if (!requestRepository.findById(reqId).get().getEventId().equals(eventId)) {
                        throw new RequestCantBeUpdatedException("Request with id = " + reqId + " was not sent "
                                + "for event with id = " + eventId);
                    } else {
                        Request request = requestRepository.findById(reqId).get();
                        request.setRequestState(RequestState.REJECTED);
                        return RequestMapper.toRequestDto(requestRepository.save(request));
                    }
                }
            }
        }
    }
}
