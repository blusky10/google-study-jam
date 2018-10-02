package com.peanutbutter.mail.repository;

import com.peanutbutter.mail.entity.ReservedMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRepository extends JpaRepository<ReservedMail, Long> {
    
    Optional<ReservedMail> findById(Long aLong);
}
