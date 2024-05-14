package smr.shop.ticket.service.helper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.repository.TicketRepository;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TicketServiceHelper {
    TicketRepository ticketRepository;

    public Ticket getById(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Ticket given by id not found!"));
    }
}