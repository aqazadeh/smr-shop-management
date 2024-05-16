package smr.shop.ticket.service.dto.ticketMessage.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Value
public class CreateTicketMessageRequest {
    @NotNull(message = "Ticket ID is required!")
     UUID ticketId;
    @NotBlank(message = "Message is required!")
     String message;
}