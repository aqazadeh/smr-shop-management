package smr.shop.ticket.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.Helper.UserHelper;
import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.model.TicketMessage;

import java.util.UUID;

import static smr.shop.ticket.service.model.valueobject.TicketStatus.ACTIVE;

@Component
public class TicketServiceMapper {

    public GetTicketMessageResponse mapToResponse(TicketMessage ticketMessage) {
        return GetTicketMessageResponse.builder()
                .ticketId(ticketMessage.getTicketId())
                .message(ticketMessage.getMessage())
                .userId(ticketMessage.getUserId())
                .build();
    }

    public TicketMessage mapToTicketMessage(CreateTicketMessageRequest request) {
        return TicketMessage.builder()
                .id(UUID.randomUUID())
                .ticketId(request.getTicketId())
                .message(request.getMessage())
                .userId(UserHelper.getUserId())
                .build();
    }
    public TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketStatus(ticket.getTicketStatus().name())
                .subject(ticket.getSubject())
                .build();
    }

    public Ticket mapToTicket(CreateTicketRequest request) {
        return Ticket.builder()
                .id(UUID.randomUUID())
                .ticketStatus(ACTIVE)
                .userId(request.getUserId())
                .build();
    }
}