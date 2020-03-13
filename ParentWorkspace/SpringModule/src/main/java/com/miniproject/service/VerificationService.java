package com.miniproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miniproject.model.Verification;

@Service
public interface VerificationService {
	List<Verification> getAllVerifications();
	boolean removeVerification(int id);
}
