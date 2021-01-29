package com.example.referral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String uuid;

    String company;

    String jobId;

    @ManyToOne
    User requester;

    Blob resume;

    //adding created at / updated at columns
}
