package com.example.referral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Lob
    private byte[] file;

    public Resume(final String name, final byte[] file) {
        this.name = name;
        this.file = file;
    }
}
