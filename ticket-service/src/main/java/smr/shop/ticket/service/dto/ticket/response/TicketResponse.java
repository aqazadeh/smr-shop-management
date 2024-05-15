package smr.shop.ticket.service.dto.ticket.response;

import lombok.*;

@Value
@Builder
public class TicketResponse {
     String subject;
     String ticketStatus;
}