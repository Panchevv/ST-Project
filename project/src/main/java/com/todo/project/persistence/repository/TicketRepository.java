package com.todo.project.persistence.repository;

import com.todo.project.persistence.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository{
    List<Ticket> getAll();

    Ticket getTicketsById(int ticketId);

    List<Ticket> getTicketsFromUser(int userId);

    void create(Ticket ticket);

    void update(Ticket ticket);

    void delete(Ticket ticket);
}


// TODO: create the other needed repository for each model we have