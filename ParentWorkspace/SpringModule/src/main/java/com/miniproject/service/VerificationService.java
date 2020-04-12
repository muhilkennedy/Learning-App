package com.miniproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miniproject.model.Verification;

@Service
public interface VerificationService {
	List<Verification> getAllVerifications();
	void removeVerification(Verification verification);
	void saveVerification(Verification verify) throws Exception;
}
