package com.miniproject.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private VerificationRepository verificationRepo;

	@Override
	public List<Verification> getAllVerifications() {
		return verificationRepo.findAll();
	}

	@Override
	public void removeVerification(Verification verification) {
		verificationRepo.delete(verification);
	}

	@Override
	public void saveVerification(Verification verify) throws Exception {
		verificationRepo.save(verify);		
	}

}
