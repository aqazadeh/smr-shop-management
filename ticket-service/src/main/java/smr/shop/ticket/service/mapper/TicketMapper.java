package smr.shop.ticket.service.mapper;

import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.model.Ticket;

public interface TicketMapper {

    TicketResponse mapToResponse(Ticket ticket);

    Ticket mapToTicket(CreateTicketRequest request);

}