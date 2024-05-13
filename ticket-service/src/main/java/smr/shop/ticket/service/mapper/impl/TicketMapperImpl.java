package smr.shop.ticket.service.mapper.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.helper.TicketServiceHelper;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.model.Ticket;


import static smr.shop.ticket.service.model.valueobject.TicketStatus.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TicketMapperImpl implements TicketMapper {
    TicketServiceHelper ticketServiceHelper;

    @Override
    public TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketStatus(ticket.getTicketStatus().name())
                .subject(ticket.getSubject())
                .createdAt(ticketServiceHelper.dateToString(ticket.getCreatedAt()))
                .updatedAt(ticketServiceHelper.dateToString(ticket.getUpdatedAt()))
                .build();

    }

    @Override
    public Ticket mapToTicket(CreateTicketRequest request) {
        return Ticket.builder()
                .ticketStatus(ACTIVE)
                .userId(request.getUserId())
                .build();
    }

    @Override
    public void mapForUpdate(UpdateTicketRequest request, Ticket ticket) {
        if (request.getSubject() != null)
            ticket.setSubject(request.getSubject());
        if (request.getUserId() != null)
            ticket.setUserId(request.getUserId());
    }
}