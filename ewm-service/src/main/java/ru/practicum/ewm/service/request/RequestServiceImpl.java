package ru.practicum.ewm.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.event.State;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestDto;
import ru.practicum.ewm.storage.request.RequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public RequestDto create(Long userId, Long eventId) {
        Request request = new Request();
        request.setEventId(eventId);
        request.setUserId(userId);
        request.setState(State.PENDING);
        request.setCreated(LocalDateTime.now());
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public void cancel(Long userId, Long requestId) {
        requestRepository.deleteById(requestId);
    }

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
}
