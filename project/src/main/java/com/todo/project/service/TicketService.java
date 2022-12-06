package com.todo.project.service;

import com.todo.project.persistence.model.Ticket;
import com.todo.project.persistence.model.User;

import java.util.List;

public interface TicketService {

    void createTicket(Ticket ticket, User createdBy);

    void updateTicket(Ticket ticket);

    List<Ticket> findTickets(Integer userId);

    Ticket saveTicker(Ticket ticket);

}
