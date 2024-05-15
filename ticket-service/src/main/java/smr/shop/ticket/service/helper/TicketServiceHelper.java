package smr.shop.ticket.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.repository.TicketRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketServiceHelper {
    private final TicketRepository ticketRepository;

    public Ticket getById(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Ticket given by id not found!"));
    }
}