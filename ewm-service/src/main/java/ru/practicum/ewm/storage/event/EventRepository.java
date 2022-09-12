package ru.practicum.ewm.storage.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.event.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
