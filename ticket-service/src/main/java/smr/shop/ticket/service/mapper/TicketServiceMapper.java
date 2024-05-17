package smr.shop.ticket.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.model.TicketMessage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

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
        UUID userId = UserHelper.getUserId();
        return TicketMessage.builder()
                .id(UUID.randomUUID())
                .message(request.getMessage())
                .userId(userId)
                .build();
    }

    public TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketStatus(ticket.getTicketStatus().name())
                .subject(ticket.getSubject())
                .build();
    }

    public Ticket mapToTicket(CreateTicketRequest request) {
        UUID userId = UserHelper.getUserId();
        return Ticket.builder()
                .id(UUID.randomUUID())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .userId(userId)
                .subject(request.getSubject())
                .build();
    }
}