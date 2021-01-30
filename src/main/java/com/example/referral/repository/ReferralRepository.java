package com.example.referral.repository;

import com.example.referral.model.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, String> {
}
