package ru.practicum.ewm.storage.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с событиями
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorIdOrderByIdAsc(Long userId, Pageable pageable);

    @Query(value = "select * from event "
            + "where event.initiator_id in (:users) and event.state in (:states) "
            + "and event.category_id in (:categories) and event_date >= :rangeStart and "
            + "event_date <= :rangeEnd order by id asc",
            nativeQuery = true)
    List<Event> findAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    List<Event> findByCategoryId(Long catId);

    @Query(value = "select * from event where event_date = (select min(event_date) from event) limit 1",
    nativeQuery = true)
    Event findMinDate();

    @Query(value = "select * from event where event_date = (select max(event_date) from event) limit 1",
            nativeQuery = true)
    Event findMaxDate();

    Event findByInitiatorIdAndId(Long userId, Long eventId);

    @Query(value = "select * from event "
            + "where (upper(event.title) ilike upper(concat('%', :text, '%')) "
            + "or upper(event.annotation) ilike upper(('%', :text, '%'))) "
            + "and event.category_id in (:categories) and event.paid = :paid "
            + "and event.event_date >= :date and event.state = :state",
            nativeQuery = true)
    List<Event> getAllPublicAfterNow(String text, List<Integer> categories, Boolean paid, LocalDateTime date,
                                     String state);

    @Query(value = "select * from event "
            + "where (upper(event.title) ilike upper(concat('%', :text, '%')) "
            + "or upper(event.annotation) ilike upper(concat('%', :text, '%'))) "
            + "and event.category_id in (:categories) and event.paid = :paid "
            + "and event.event_date >= :rangeStart and event.event_date <= :rangeEnd and event.state = :state",
            nativeQuery = true)
    List<Event> getAllPublicInRange(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, String state);
}
