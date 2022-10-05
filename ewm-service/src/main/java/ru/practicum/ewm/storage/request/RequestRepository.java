package ru.practicum.ewm.storage.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestState;

import java.util.List;

/**
 * Репозиторий для работы с заявками на участие в событиях
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

    List<Request> findByUserIdAndEventIdAndRequestStateIsNot(Long userId, Long eventId, RequestState requestState);

    List<Request> findByEventIdAndRequestStateIsNot(Long eventId, RequestState requestState);

    List<Request> findByEventIdAndRequestStateIsNotIn(Long eventId, List<RequestState> requestStates);

    List<Request> findByEventId(Long eventId);

    List<Request> findByEventIdAndRequestState(Long eventId, RequestState requestState);
}
