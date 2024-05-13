package smr.shop.ticket.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

@Value
public class UpdateTicketStatusRequest {
    @NotBlank TicketStatus status;
}
