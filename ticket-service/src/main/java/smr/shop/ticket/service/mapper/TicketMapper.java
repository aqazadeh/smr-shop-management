package smr.shop.ticket.service.mapper;

import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.Ticket;

public interface TicketMapper {

    TicketResponse mapToResponse(Ticket ticket);

    Ticket mapToTicket(CreateTicketRequest request);

    void mapForUpdate(UpdateTicketRequest request, Ticket ticket);

}