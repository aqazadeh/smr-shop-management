package smr.shop.libs.outbox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class OutboxEntity {

    @Id
    private UUID id;

    private String event;

    private String payload;

    public static OutboxEntity of(String eventType, String payload) {
        return OutboxEntity.builder()
                .id(UUID.randomUUID())
                .event(eventType)
                .payload(payload).build();
    }
}
