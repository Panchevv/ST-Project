package com.todo.project.controller;

import com.todo.project.payload.response.TicketResponse;
import com.todo.project.persistence.model.Ticket;
import com.todo.project.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllTickets(){
        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketResponse> result = new ArrayList<>();

        for(Ticket ticket: tickets){
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.setCreator(ticket.getCreator());
            ticketResponse.setTitle(ticket.getTitle());
            ticketResponse.setDescription(ticket.getDescription());
            ticketResponse.setDueDate(ticket.getDueDate());

            result.add(ticketResponse);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findTicketById(Long id){
        Ticket ticket = ticketService.findTicketById(id);
        if(ticket == null){
            return new ResponseEntity<>("Incorrect id.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticket,HttpStatus.OK);
    }

    @PostMapping
    public void createTicket() {
        // TODO:
        // Receive ticket object in the body and save it in the database
    }

    @PostMapping
    public void deleteTicker(@RequestParam Integer id) {
        // TODO:
        // Delete the ticket whic id we have recieved as parameter
    }

    @PutMapping
    public void updateTicker() {
        // TODO:
        // Change the ticket values
    }

}



// TODO: create controller for each separate model operations we are going to support 
