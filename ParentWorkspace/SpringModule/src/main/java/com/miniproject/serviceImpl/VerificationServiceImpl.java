package com.miniproject.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Verification;
import com.miniproject.repository.VerificationRepository;
import com.miniproject.service.VerificationService;

/**
 * @author MuhilKennedy
 *
 */
@Service
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	VerificationRepository verificationRepo;

	@Override
	public List<Verification> getAllVerifications() {
		return verificationRepo.findAll();
	}

	@Override
	public boolean removeVerification(int id) {
		verificationRepo.deleteById(id);
		return false;
	}
	
	
}
