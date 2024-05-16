package smr.shop.ticket.service.dto.ticket.response;

import lombok.*;

@Data
@Builder
public class TicketResponse {
     String subject;
     String ticketStatus;
}