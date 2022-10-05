package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.event.EventDtoWithViews;

import java.util.Comparator;

/**
 * Класс-компаратор для сравнения событий по дате
 */
public class EventDateComparator implements Comparator<EventDtoWithViews> {
    @Override
    public int compare(EventDtoWithViews o1, EventDtoWithViews o2) {
        if (o1.getEventDate().isEqual(o2.getEventDate())) {
            return o1.getId().compareTo(o2.getId());
        } else {
            return o1.getEventDate().compareTo(o2.getEventDate());
        }
    }
}
