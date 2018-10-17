package com.peanutbutter.mail.repository;

import com.peanutbutter.mail.entity.ReservedResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservedResourceRepository extends JpaRepository<ReservedResource, Long> {

    Optional<ReservedResource> findById(Long aLong);
}
