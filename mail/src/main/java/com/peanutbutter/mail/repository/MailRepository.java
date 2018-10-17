package com.peanutbutter.mail.repository;

import com.peanutbutter.mail.entity.SendMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRepository extends JpaRepository<SendMail, Long> {
}
