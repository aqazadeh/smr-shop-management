package smr.shop.ticket.service.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class TicketResponse {
     UUID id;
     String subject;
     String ticketStatus;
}