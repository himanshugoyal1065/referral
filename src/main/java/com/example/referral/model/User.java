package com.example.referral.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

    @NotNull
    @Id
    String email;

    @Nullable
    @OneToMany(mappedBy = "requester")
    List<Referral> referralsTaken;

    @OneToMany(mappedBy = "requester")
    List<Referral> referralsGiven;

    @ManyToOne
    @JoinColumn(name = "company")
    @Nullable
    Company company;

    @Nullable
    @OneToOne
    Resume resume;

    @NotNull
    String name;

    @Nullable
    String mobileNumber;

    @Nullable
    String imageUrl;

    @Nullable
    @Enumerated(EnumType.STRING)
    AuthProvider provider;

    String providerId;
}
