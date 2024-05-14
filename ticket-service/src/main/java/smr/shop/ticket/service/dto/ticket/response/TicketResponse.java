package smr.shop.ticket.service.dto.ticket.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class TicketResponse {
    String subject;
    String ticketStatus;
}