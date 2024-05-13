package smr.shop.ticket.service.service;

import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketStatusRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.Ticket;

import java.util.UUID;

public interface TicketService {
    TicketResponse createTicket(CreateTicketRequest request);

    TicketResponse updateTicketStatus(UUID id, UpdateTicketStatusRequest request);

    void deleteTicket(UUID id);

    Ticket findById(UUID id);
}
