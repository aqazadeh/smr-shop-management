package smr.shop.ticket.service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetTicketResponse {
    String subject;
    String ticketStatus;
    String createdAt;
    String updatedAt;
}