package smr.shop.ticket.service.service;

import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.GetTicketResponse;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    GetTicketResponse getById(UUID id);

    List<GetTicketResponse> getAll();

    CreateTicketRequest add(CreateTicketRequest request);

    GetTicketResponse update(UUID id, UpdateTicketRequest request);

    void deleteById(UUID id);
}