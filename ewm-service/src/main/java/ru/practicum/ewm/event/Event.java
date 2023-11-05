package ru.practicum.ewm.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.event.enums.EventState;
import ru.practicum.ewm.request.ParticipationRequest;
import ru.practicum.ewm.request.enums.ParticipationRequestStatus;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "events", schema = "public")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<ParticipationRequest> requests;

    @CreationTimestamp
    private LocalDateTime createdOn;

    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Embedded
    private Location location;

    @Column(nullable = false)
    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(nullable = false)
    private String title;

    public Collection<ParticipationRequest> getConfirmedRequests() {
        if (this.getRequests() == null) {
            return new ArrayList<>();
        }
        return this.getRequests().stream()
                .filter((request) -> request.getStatus() == ParticipationRequestStatus.CONFIRMED)
                .collect(Collectors.toUnmodifiableList());
    }
}
