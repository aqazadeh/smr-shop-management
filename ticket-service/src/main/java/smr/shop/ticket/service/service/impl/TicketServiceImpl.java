package smr.shop.ticket.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketStatusRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.exception.TicketException;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.repository.TicketRepository;
import smr.shop.ticket.service.service.TicketService;

import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        Ticket ticket = ticketMapper.toTicket(request);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toTicketResonse(ticket);
    }

    @Override
    public TicketResponse updateTicketStatus(UUID id, UpdateTicketStatusRequest request) {
        Ticket ticket = findById(id);
        ticket.setTicketStatus(request.getStatus());
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toTicketResonse(ticket);

    }

    @Override
    public void deleteTicket(UUID id) {
        Ticket ticket = findById(id);
        ticketRepository.delete(ticket);
    }

    @Override
    public Ticket findById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
