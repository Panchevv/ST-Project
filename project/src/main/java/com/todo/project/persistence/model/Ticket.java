package com.todo.project.persistence.model;

import javax.persistence.*;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name="creator")
    private User creator;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "dueDate")
    private Date dueDate;
}
