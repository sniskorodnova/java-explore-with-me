package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.event.EventDtoWithViews;

import java.util.Comparator;
import java.util.Objects;

/**
 * Класс-компаратор для сравнения событий по количеству просмотров
 */
public class EventViewsComparator implements Comparator<EventDtoWithViews> {
    @Override
    public int compare(EventDtoWithViews o1, EventDtoWithViews o2) {
        if (Objects.equals(o1.getViews(), o2.getViews())) {
            return o1.getId().compareTo(o2.getId());
        } else {
            return o1.getViews().compareTo(o2.getViews());
        }
    }
}
