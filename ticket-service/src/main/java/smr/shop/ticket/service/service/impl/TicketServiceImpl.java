package smr.shop.ticket.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.Helper.UserHelper;
import smr.shop.ticket.service.dto.ticket.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.ticket.response.TicketResponse;
import smr.shop.ticket.service.dto.ticketMessage.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.ticketMessage.response.GetTicketMessageResponse;
import smr.shop.ticket.service.helper.TicketServiceHelper;
import smr.shop.ticket.service.mapper.TicketMapper;
import smr.shop.ticket.service.mapper.TicketMessageMapper;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.model.TicketMessage;
import smr.shop.ticket.service.model.valueobject.TicketStatus;
import smr.shop.ticket.service.repository.TicketMessageRepository;
import smr.shop.ticket.service.repository.TicketRepository;
import smr.shop.ticket.service.service.TicketService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

import static smr.shop.libs.common.constant.ServiceConstants.pageSize;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TicketMessageRepository ticketMessageRepository;
    private final TicketServiceHelper ticketServiceHelper;
    private final TicketMessageMapper ticketMessageMapper;


    @Override
    public CreateTicketRequest createTicket(CreateTicketRequest request) {
        ticketRepository.save(ticketMapper.mapToTicket(request));
        return request;
    }

    @Override
    public List<GetTicketMessageResponse> getById(UUID ticketId, Integer page) {
        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
        return ticketMessageRepository.findAllByTicketId(ticketServiceHelper.getById(ticketId).getId(), pageable)
                .stream().map(ticketMessageMapper::mapToResponse)
                .toList();
    }

    @Override
    public List<TicketResponse> getAllUserTickets(Integer page) {
        Long userId = UserHelper.getUserId();
        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
        return ticketRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(ticketMapper::mapToResponse)
                .toList();
    }

    @Override
    public void sendMessage(UUID ticketId, CreateTicketMessageRequest request) {
        TicketMessage ticketMessage = ticketMessageMapper.mapToTicket(request);
        ticketMessageRepository.save(ticketMessage);
    }

    @Override
    public void updateTicketStatus(UUID ticketId, TicketStatus status) {
        Ticket ticket = ticketServiceHelper.getById(ticketId);
        ticket.setTicketStatus(status);
        ticketRepository.save(ticket);
    }
}