package smr.shop.ticket.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    UUID id;

    Long userId;

    String subject;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    TicketStatus ticketStatus = TicketStatus.ACTIVE;
    @CreationTimestamp
    ZonedDateTime createdAt;

    ZonedDateTime updatedAt;
}