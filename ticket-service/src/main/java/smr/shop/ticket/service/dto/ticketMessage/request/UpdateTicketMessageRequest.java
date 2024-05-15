package smr.shop.ticket.service.dto.ticketMessage.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTicketMessageRequest {
    @Min(value = 1, message = "User ID cannot be less than 1!")
    private UUID userId;
    private UUID ticketId;
    private String message;
}