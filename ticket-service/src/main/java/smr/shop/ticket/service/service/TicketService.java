package smr.shop.ticket.service.service;

import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.TicketMessageRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    CreateTicketRequest createTicket(CreateTicketRequest request);

    TicketResponse getById(UUID id, Integer page); // check user id

    List<TicketResponse> getAllUserTickets(Integer page); //check userId

    void sendMessage(UUID ticketId, TicketMessageRequest request); //check ticket is valid user

    void updateTicketStatus(UUID id, TicketStatus status);

}