package smr.shop.ticket.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.ticket.service.dto.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketMessageResponse;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.model.TicketMessage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class TicketServiceMapper {

    public TicketMessageResponse ticketMessageToResponse(TicketMessage ticketMessage) {
        return TicketMessageResponse.builder()
                .ticketId(ticketMessage.getTicketId())
                .message(ticketMessage.getMessage())
                .userId(ticketMessage.getUserId())
                .build();
    }

    public TicketMessage createTicketMessageRequestToTicketMessageResponse(CreateTicketMessageRequest request) {
        UUID userId = UserHelper.getUserId();
        return TicketMessage.builder()
                .id(UUID.randomUUID())
                .message(request.getMessage())
                .userId(userId)
                .build();
    }

    public TicketResponse ticketToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketStatus(ticket.getTicketStatus().name())
                .subject(ticket.getSubject())
                .build();
    }

    public Ticket createTicketRequestToTicket(CreateTicketRequest request) {
        UUID userId = UserHelper.getUserId();
        return Ticket.builder()
                .id(UUID.randomUUID())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .userId(userId)
                .subject(request.getSubject())
                .build();
    }
}