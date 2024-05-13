package smr.shop.ticket.service.mapper;

import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.model.TicketMessage;

public interface TicketMessageMapper {
    GetTicketMessageResponse mapToResponse(TicketMessage ticketMessage);
    TicketMessage mapToTicket(CreateTicketMessageRequest request);
}
