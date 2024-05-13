package smr.shop.ticket.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketStatusRequest;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.service.TicketService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody CreateTicketRequest request) {
        TicketResponse ticketResponse = ticketService.createTicket(request);
        return ResponseEntity.ok(ticketResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicketStatus(@PathVariable UUID id, @RequestBody UpdateTicketStatusRequest request) {
        TicketResponse ticketResponse = ticketService.updateTicketStatus(id, request);
        return ResponseEntity.ok(ticketResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
