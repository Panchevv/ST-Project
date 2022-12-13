package com.todo.project.payload.request;

import com.todo.project.persistence.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TicketRequest {
    private User creator;
    private String title;
    private String description;
    private Date dueDate;
}
