package smr.shop.ticket.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.ticket.service.dto.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketMessageResponse;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.exception.TicketServiceException;
import smr.shop.ticket.service.helper.TicketServiceHelper;
import smr.shop.ticket.service.mapper.TicketServiceMapper;
import smr.shop.ticket.service.model.Ticket;
import smr.shop.ticket.service.model.TicketMessage;
import smr.shop.ticket.service.model.valueobject.TicketStatus;
import smr.shop.ticket.service.repository.TicketMessageRepository;
import smr.shop.ticket.service.repository.TicketRepository;
import smr.shop.ticket.service.service.TicketService;

import java.util.List;
import java.util.UUID;

import static smr.shop.libs.common.constant.ServiceConstants.pageSize;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketServiceMapper ticketServiceMapper;
    private final TicketMessageRepository ticketMessageRepository;
    private final TicketServiceHelper ticketServiceHelper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketServiceHelper ticketServiceHelper,
                             TicketMessageRepository ticketMessageRepository, TicketServiceMapper ticketServiceMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketServiceHelper = ticketServiceHelper;
        this.ticketMessageRepository = ticketMessageRepository;
        this.ticketServiceMapper = ticketServiceMapper;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public CreateTicketRequest createTicket(CreateTicketRequest request) {
        Ticket ticket = ticketServiceMapper.createTicketRequestToTicket(request);
        System.out.println(ticket.getUserId());
        ticketRepository.save(ticket);
        return request;
    }


//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateTicketStatus(UUID ticketId, TicketStatus status) {
        Ticket ticket = ticketServiceHelper.getById(ticketId);
        ticket.setTicketStatus(status);
        ticketRepository.save(ticket);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<TicketMessageResponse> getById(UUID ticketId, Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ticketMessageRepository.findAllByTicketId(ticketServiceHelper.getById(ticketId).getId(), pageable)
                .stream().map(ticketServiceMapper::ticketMessageToResponse)
                .toList();
    }

    @Override
    public List<TicketResponse> getAllUserTickets(Integer page) {
        UUID userId = UserHelper.getUserId();
        Pageable pageable = PageRequest.of(page, pageSize);
        return ticketRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(ticketServiceMapper::ticketToTicketResponse)
                .toList();
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    @Transactional
    public void sendMessageByUser(UUID ticketId, CreateTicketMessageRequest request) {
        Ticket ticket = ticketServiceHelper.getById(ticketId);
        if(ticket.getTicketStatus() == TicketStatus.CLOSED) {
            throw new TicketServiceException("This ticket is disabled", HttpStatus.BAD_REQUEST);
        }
        if (!UserHelper.getUserId().toString().equals(ticket.getUserId().toString()))
            throw new TicketServiceException("Ticket given by id doesn't belongs to current user!", HttpStatus.FORBIDDEN);
        TicketMessage ticketMessage = ticketServiceMapper.createTicketMessageRequestToTicketMessageResponse(request);
        ticketMessage.setTicketId(ticketId);
        ticketMessageRepository.save(ticketMessage);
    }

    @Override
    @Transactional
    public void sendMessageByAdmin(UUID ticketId, CreateTicketMessageRequest request) {
        Ticket ticket = ticketServiceHelper.getById(ticketId);
        TicketMessage ticketMessage = ticketServiceMapper.createTicketMessageRequestToTicketMessageResponse(request);
        ticketMessage.setTicketId(ticket.getId());
        ticketMessageRepository.save(ticketMessage);
    }

}