package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.location.Location;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс, описывающий модель события
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
