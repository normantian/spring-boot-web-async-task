package com.norman.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    int id;
    String name;
    String email;
    Date date;

    public Customer(int id, String name, String email, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
    }
}
