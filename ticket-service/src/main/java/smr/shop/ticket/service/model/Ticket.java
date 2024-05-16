package smr.shop.ticket.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private UUID userId;
    private String subject;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TicketStatus ticketStatus = TicketStatus.ACTIVE;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}