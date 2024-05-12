package smr.shop.ticket.service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.GetTicketResponse;
import smr.shop.ticket.service.helper.TicketServiceHelper;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.model.Ticket;
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
    public GetTicketResponse getById(UUID id) {
        return ticketMapper.mapToResponse(ticketServiceHelper.getById(id));
    }

    @Override
    public List<GetTicketResponse> getAll() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::mapToResponse)
                .toList();
    }

    @Override
    public CreateTicketRequest add(CreateTicketRequest request) {
        ticketRepository.save(ticketMapper.mapToTicket(request));
        return request;
    }

    @Override
    public GetTicketResponse update(UUID id, UpdateTicketRequest request) {
        Ticket ticket = ticketServiceHelper.getById(id);
        ticketMapper.mapForUpdate(request, ticket);
        return ticketMapper.mapToResponse(ticket);
    }

    @Override
    public void deleteById(UUID id) {
        ticketServiceHelper.getById(id);
        ticketRepository.deleteById(id);
    }
}