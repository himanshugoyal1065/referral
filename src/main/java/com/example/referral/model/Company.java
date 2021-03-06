package com.example.referral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Company {

    @Id
    String name;

    @OneToOne
    User user;

    public Company(final String name) {
        this.name = name;
    }

}
