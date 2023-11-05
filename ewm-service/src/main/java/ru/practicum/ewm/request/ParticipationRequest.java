package ru.practicum.ewm.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.request.enums.ParticipationRequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests", schema = "public")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

    @Column(nullable = false)
    private Long requesterId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipationRequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    public static ParticipationRequest of(Long userId, Long eventId) {
       return new ParticipationRequest()
                .setRequesterId(userId)
                .setId(eventId)
                .setEvent(new Event())
                .setStatus(ParticipationRequestStatus.PENDING);
    }
}
