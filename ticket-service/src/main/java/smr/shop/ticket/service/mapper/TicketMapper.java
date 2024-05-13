package smr.shop.ticket.service.mapper;

import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.GetTicketResponse;
import smr.shop.ticket.service.model.Ticket;

public interface TicketMapper {

    GetTicketResponse mapToResponse(Ticket ticket);

    Ticket mapToTicket(CreateTicketRequest request);

    void mapForUpdate(UpdateTicketRequest request, Ticket ticket);

}