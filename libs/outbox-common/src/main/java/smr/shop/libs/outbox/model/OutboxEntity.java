package smr.shop.libs.outbox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class OutboxEntity {

    @Id
    private UUID id;

    private String eventType;

    private String eventPayload;
}
