package smr.shop.ticket.service.mapper.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.model.Ticket;


import java.util.UUID;

import static smr.shop.ticket.service.model.valueobject.TicketStatus.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TicketMapperImpl implements TicketMapper {

    @Override
    public TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketStatus(ticket.getTicketStatus().name())
                .subject(ticket.getSubject())
                .build();
    }

    @Override
    public Ticket mapToTicket(CreateTicketRequest request) {
        return Ticket.builder()
                .id(UUID.randomUUID())
                .ticketStatus(ACTIVE)
                .userId(request.getUserId())
                .build();
    }

}