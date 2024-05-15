package smr.shop.ticket.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TicketMessage {
    @Id
    private UUID id;
    private UUID userId;
    private UUID ticketId;
    private String message;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}