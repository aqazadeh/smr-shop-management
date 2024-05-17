package smr.shop.ticket.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CreateTicketMessageRequest {
    @NotBlank(message = "Message is required!")
    String message;
}