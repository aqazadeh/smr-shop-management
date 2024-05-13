package smr.shop.ticket.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.Ticket;

import java.util.UUID;

@Component
public class TicketMapper {
    public Ticket toTicket(CreateTicketRequest request) {
        Ticket.TicketBuilder builder = Ticket.builder();
        builder.userId(request.getUserId());
        builder.subject(request.getSubject());
        return builder.build();
    }

    public TicketResponse toTicketResonse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .subject(ticket.getSubject())
                .status(ticket.getTicketStatus())
                .build();
    }
}
