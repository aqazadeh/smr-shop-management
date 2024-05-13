package smr.shop.ticket.service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.TicketMessageRequest;
import smr.shop.ticket.service.dto.response.TicketMessageResponse;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.helper.TicketServiceHelper;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.model.valueobject.TicketStatus;
import smr.shop.ticket.service.repository.TicketRepository;
import smr.shop.ticket.service.service.TicketService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;
    TicketMapper ticketMapper;
    TicketServiceHelper ticketServiceHelper;


    @Override
    public CreateTicketRequest createTicket(CreateTicketRequest request) {
        ticketRepository.save(ticketMapper.mapToTicket(request));
        return request;
    }

    @Override
    public List<TicketMessageResponse> getById(UUID ticketId, Integer page) {
        return List.of();
    }

    @Override
    public List<TicketResponse> getAllUserTickets(Integer page) {
        return List.of();
    }

    @Override
    public void sendMessage(UUID ticketId, TicketMessageRequest request) {

    }

    @Override
    public void updateTicketStatus(UUID ticketId, TicketStatus status) {

    }


}