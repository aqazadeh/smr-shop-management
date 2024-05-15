package smr.shop.ticket.service.dto.ticketMessage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTicketMessageResponse {
    private UUID userId;
    private UUID ticketId;
    private String message;
}