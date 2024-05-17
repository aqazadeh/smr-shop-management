package smr.shop.ticket.service.dto.ticketMessage.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CreateTicketMessageRequest {
    @NotBlank(message = "Message is required!")
    String message;
}