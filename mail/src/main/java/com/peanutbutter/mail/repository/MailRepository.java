package com.peanutbutter.mail.repository;

import com.peanutbutter.mail.entity.ReservedMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<ReservedMail, Long> {
}
