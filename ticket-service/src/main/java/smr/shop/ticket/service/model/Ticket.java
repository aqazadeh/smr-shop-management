package smr.shop.ticket.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {
    @Id
    private UUID id;

    private Long userId;

    private String subject;

    private TicketStatus ticketStatus;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}