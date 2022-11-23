package com.todo.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {

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
