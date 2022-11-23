package com.todo.project.service;

import com.todo.project.persistence.model.Ticket;
import java.util.List;

public interface TicketService {

    Ticket createTicket();

    Ticket updateTicket();

    List<Ticket> findTickets(Integer userId);

    Ticket saveTicker(Ticket ticket);

}
