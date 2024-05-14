package smr.shop.ticket.service.service;

import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.model.valueobject.TicketStatus;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    CreateTicketRequest createTicket(CreateTicketRequest request);
    List<GetTicketMessageResponse> getById(UUID ticketId, Integer page); // check user id
    List<TicketResponse> getAllUserTickets(Integer page); //check userId
    void sendMessage(UUID ticketId, CreateTicketMessageRequest request); //check ticket is valid user
    void updateTicketStatus(UUID ticketId, TicketStatus status);
}