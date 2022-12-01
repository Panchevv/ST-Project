package com.todo.project.persistence.repository;

import com.todo.project.exceptions.EntityNotFoundException;
import com.todo.project.persistence.model.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository{
    private final SessionFactory sessionFactory;

    public TicketRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Ticket> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Ticket> ticketQuery = session.createQuery("from Ticket", Ticket.class);
            return ticketQuery.list();
        }
    }

    @Override
    public Ticket getTicketsById(int ticketId) {
        try (Session session = sessionFactory.openSession()) {
            Ticket ticket = session.get(Ticket.class, ticketId);
            if (ticket == null) {
                throw new EntityNotFoundException("Ticket", ticketId);
            }
            return ticket;
        }
    }

    @Override
    public List<Ticket> getTicketsFromUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Ticket> postQuery = session.createQuery("FROM Ticket WHERE creator = :id", Ticket.class);
            postQuery.setParameter("id", userId);
            return postQuery.list();
        }
    }

    @Override
    public void create(Ticket ticket) {
        try (Session session = sessionFactory.openSession()) {
            session.save(ticket);
        }
    }

    @Override
    public void update(Ticket ticket) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(ticket);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(ticket);
        }
    }
}
