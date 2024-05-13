package smr.shop.ticket.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

import java.util.UUID;

@Value
@Builder
public class TicketResponse {
    UUID id;
    Long userId;
    String subject;
    TicketStatus status;
}
