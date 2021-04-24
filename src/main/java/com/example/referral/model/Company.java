package com.example.referral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Company {

    @Id
    String name;

    @OneToMany
    List<User> users;

    public Company(final String name) {
        this.name = name;
    }

    public void setUserToList(final User user) {
        List<User> users = this.getUsers();
        users.add(user);
        this.setUsers(users);
    }
}
