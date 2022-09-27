package ru.practicum.ewm.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.event.Event;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс, описывающий dto подборки событий для бд
 */
@Entity
@Table(name = "compilation", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Boolean pinned;
    @ManyToMany
    @JoinTable(name = "compilation_event", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    Set<Event> events;
}
