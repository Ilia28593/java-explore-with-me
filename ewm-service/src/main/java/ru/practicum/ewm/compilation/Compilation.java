package ru.practicum.ewm.compilation;

import lombok.*;
import ru.practicum.ewm.event.Event;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "compilations", schema = "public")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Collection<Event> events;

    @Column(nullable = false)
    private Boolean pinned;

    @Column(nullable = false)
    private String title;
}
