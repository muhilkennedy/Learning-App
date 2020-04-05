package com.miniproject.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.Verification;
import com.miniproject.repository.VerificationRepository;
import com.miniproject.service.VerificationService;

/**
 * @author MuhilKennedy
 *
 */
@Service
@Transactional
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	VerificationRepository verificationRepo;

	@Override
	public List<Verification> getAllVerifications() {
		return verificationRepo.findAll();
	}

	@Override
	@Modifying
	public void removeVerification(Verification verification) {
		verificationRepo.delete(verification);
	}
	
}
