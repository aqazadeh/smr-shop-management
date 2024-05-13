package smr.shop.ticket.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateTicketRequest {
    @NotBlank Long userId;
    @NotBlank String subject;
}
