package com.todo.project.service.impl;

import com.todo.project.exceptions.EntityNotFoundException;
import com.todo.project.persistence.model.Ticket;
import com.todo.project.persistence.model.User;
import com.todo.project.persistence.repository.TicketRepository;
import com.todo.project.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void createTicket(Ticket ticket) {

        ticketRepository.save(ticket);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        if(ticket.getId() == null){
            throw new EntityNotFoundException("Ticket", ticket.getId());
        }
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findTicketsByCreator(Long userId) {
        return ticketRepository.findTicketByCreator(userId);
    }

    @Override
    public Ticket findTicketById(Long ticketId){
        return ticketRepository.findTicketById(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }
}
