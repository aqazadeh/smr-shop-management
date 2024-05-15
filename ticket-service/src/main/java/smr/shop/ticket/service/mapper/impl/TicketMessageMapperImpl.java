package smr.shop.ticket.service.mapper.impl;

import org.springframework.stereotype.Service;
import smr.shop.libs.common.Helper.UserHelper;
import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.mapper.TicketMessageMapper;
import smr.shop.ticket.service.model.TicketMessage;

import java.util.UUID;

@Service
public class TicketMessageMapperImpl implements TicketMessageMapper {

    @Override
    public GetTicketMessageResponse mapToResponse(TicketMessage ticketMessage) {
        return GetTicketMessageResponse.builder()
                .ticketId(ticketMessage.getTicketId())
                .message(ticketMessage.getMessage())
                .userId(ticketMessage.getUserId())
                .build();
    }

    @Override
    public TicketMessage mapToTicket(CreateTicketMessageRequest request) {
        return TicketMessage.builder()
                .id(UUID.randomUUID())
                .ticketId(request.getTicketId())
                .message(request.getMessage())
                .userId(UserHelper.getUserId())
                .build();
    }

}