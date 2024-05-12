package smr.shop.ticket.service.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTicketRequest {
    @Min(value = 1, message = "User id cannot be less than 1!")
    Long userId;
    String subject;
}