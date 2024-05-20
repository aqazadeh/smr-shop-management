package smr.shop.ticket.service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class TicketMessageResponse {
     UUID userId;
     UUID ticketId;
     String message;
}
