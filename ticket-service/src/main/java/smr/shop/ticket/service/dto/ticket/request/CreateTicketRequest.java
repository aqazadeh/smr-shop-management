package smr.shop.ticket.service.dto.ticket.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTicketRequest {
    @NotNull(message = "User id is required!")
    @Min(value = 1, message = "User id cannot be less than 1!")
    Long userId;
    @NotBlank(message = "Subject is required!")
    String subject;
}