package ru.practicum.ewm.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс, описывающий dto события для бд
 */
@Entity
@Table(name = "event", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String annotation;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false)
    private Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private Long participantLimit;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private EventState eventState;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
}
