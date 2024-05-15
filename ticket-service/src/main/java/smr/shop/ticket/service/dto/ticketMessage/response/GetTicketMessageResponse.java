package smr.shop.ticket.service.dto.ticketMessage.response;

import lombok.*;

import java.util.UUID;
@Value
@Builder
public class GetTicketMessageResponse {
     UUID userId;
     UUID ticketId;
     String message;
}