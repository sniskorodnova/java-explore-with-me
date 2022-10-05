package ru.practicum.ewm.model.compilationevent;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompilationEventId implements Serializable {
    private Long compilationId;
    private Long eventId;

    public CompilationEventId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompilationEventId that = (CompilationEventId) o;
        return Objects.equals(compilationId, that.compilationId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compilationId, eventId);
    }
}
