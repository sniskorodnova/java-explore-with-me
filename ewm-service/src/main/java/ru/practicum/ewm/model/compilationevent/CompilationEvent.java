package ru.practicum.ewm.model.compilationevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс, описывающий модель связи подборки с событиями
 */
@Entity
@IdClass(CompilationEventId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationEvent {
    @Id
    @Column(name = "compilation_id", nullable = false)
    private Long compilationId;
    @Id
    @Column(name = "event_id", nullable = false)
    private Long eventId;
}
