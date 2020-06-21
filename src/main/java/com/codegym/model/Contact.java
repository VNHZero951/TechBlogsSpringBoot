package com.codegym.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String GuestName;
    @NotNull
    @Email(message = "Email should be valid")
    private String Email;
    @Size(min = 10, max = 2000, message
            = "Contact min 10 characters")
    private String Text;

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        GuestName = guestName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
