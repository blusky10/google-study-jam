package com.peanutbutter.mail.repository;

import com.peanutbutter.mail.model.MailContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailContent, Long> {
}
