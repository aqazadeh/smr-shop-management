package smr.shop.ticket.service.dto.ticketMessage.request;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;
@Value
public class UpdateTicketMessageRequest {
    @Min(value = 1, message = "User ID cannot be less than 1!")
     UUID userId;
     UUID ticketId;
     String message;
}