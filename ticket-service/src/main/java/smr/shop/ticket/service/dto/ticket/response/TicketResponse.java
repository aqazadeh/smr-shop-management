package smr.shop.ticket.service.dto.ticket.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private String subject;
    private String ticketStatus;
}