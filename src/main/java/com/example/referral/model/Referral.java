package com.example.referral.model;

import com.example.referral.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long uuid;

    String company;

    String jobId;

    String name;

    @ManyToOne
    User requester;

    @OneToOne
    Resume resume;

    String status = Constants.CREATED;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
