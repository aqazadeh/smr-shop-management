package smr.shop.ticket.service.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import smr.shop.ticket.service.dto.request.CreateTicketRequest;
import smr.shop.ticket.service.dto.request.UpdateTicketRequest;
import smr.shop.ticket.service.dto.response.GetTicketResponse;
import smr.shop.ticket.service.service.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TicketController{
    TicketService ticketService;
    @GetMapping("/get-by-id/{id}")
    public GetTicketResponse getById(@PathVariable UUID id){
        return ticketService.getById(id);
    }
    @GetMapping("/get-all")
    public List<GetTicketResponse> getAll(){
        return ticketService.getAll();
    }
    @PostMapping("/add")
    public CreateTicketRequest add(@RequestBody CreateTicketRequest request){
        return ticketService.add(request);
    }
    @PutMapping("/update/{id}")
    public GetTicketResponse update(@PathVariable UUID id,
                                    @RequestBody UpdateTicketRequest request){
        return ticketService.update(id,request);
    }
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable UUID id){
        ticketService.deleteById(id);
    }
}