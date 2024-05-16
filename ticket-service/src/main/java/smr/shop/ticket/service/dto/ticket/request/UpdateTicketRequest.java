package smr.shop.ticket.service.dto.ticket.request;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Value
public class UpdateTicketRequest {
    @Min(value = 1, message = "User id cannot be less than 1!")
    UUID userId;
    String subject;
}