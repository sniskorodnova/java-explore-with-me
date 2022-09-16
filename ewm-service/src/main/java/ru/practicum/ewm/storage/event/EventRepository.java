package ru.practicum.ewm.storage.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiator_IdOrderByIdAsc(Long userId, Pageable pageable);

    @Query(value = "select * from event "
            + "where event.initiator_id in (:users) and event.state in (:states) "
            + "and event.category_id in (:categories) and event_date >= :rangeStart and "
            + "event_date <= :rangeEnd order by id asc",
            nativeQuery = true)
    List<Event> findAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}
