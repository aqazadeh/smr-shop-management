package smr.shop.ticket.service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.ticket.service.dto.request.CreateTicketMessageRequest;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.response.TicketMessageResponse;
import smr.shop.ticket.service.dto.response.TicketResponse;
import smr.shop.ticket.service.model.valueobject.TicketStatus;
import smr.shop.ticket.service.service.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


//    ----------------------------------- Post -----------------------------------

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateTicketRequest add(@RequestBody @Valid CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PostMapping("/{ticketId}/messages/")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@PathVariable UUID ticketId,
                            @RequestBody @Valid CreateTicketMessageRequest request) {
        ticketService.sendMessageByUser(ticketId, request);
    }

    @PostMapping("/{ticketId}/messages/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessageByAdmin(@PathVariable UUID ticketId,
                                   @RequestBody @Valid CreateTicketMessageRequest request) {
        ticketService.sendMessageByAdmin(ticketId, request);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateStatus(@PathVariable UUID ticketId,
                                                      @RequestParam("ticketStatus") @Valid TicketStatus ticketstatus) {
        ticketService.updateTicketStatus(ticketId, ticketstatus);
        return ResponseEntity.ok().body(EmptyResponse.builder().message("updated success").build());
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping("/{ticketId}/messages")
    public List<TicketMessageResponse> getById(@PathVariable UUID ticketId,
                                               @Valid @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ticketService.getById(ticketId, page);
    }

    @GetMapping
    public List<TicketResponse> getAllUserTickets(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ticketService.getAllUserTickets(page);
    }

}