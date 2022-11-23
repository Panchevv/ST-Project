package com.todo.project.persistence.repository;

import com.todo.project.persistence.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}


// TODO: create the other needed repository for each model we have