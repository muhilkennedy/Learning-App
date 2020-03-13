package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.model.Verification;;

public interface VerificationRepository extends JpaRepository<Verification, Integer> {

}
