package com.greenimpact.server.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
