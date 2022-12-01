package com.todo.project.service.impl;

import com.todo.project.exceptions.EntityNotFoundException;
import com.todo.project.persistence.model.Ticket;
import com.todo.project.persistence.model.User;
import com.todo.project.persistence.repository.TicketRepository;
import com.todo.project.service.TicketService;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void createTicket(Ticket ticket, User createdBy) {

        ticketRepository.create(ticket);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        ticketRepository.update(ticket);
    }

    @Override
    public List<Ticket> findTickets(Integer userId) { //?
        return null;
    }

    @Override
    public Ticket saveTicker(Ticket ticket) { //?
        return null;
    }
}
