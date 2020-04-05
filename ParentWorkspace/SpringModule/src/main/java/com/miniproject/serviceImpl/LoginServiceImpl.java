package com.miniproject.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.messages.GenericResponse;
import com.miniproject.model.User;
import com.miniproject.model.Verification;
import com.miniproject.repository.UserRepository;
import com.miniproject.service.LoginService;
import com.miniproject.service.VerificationService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.ResponseUtil;
import com.miniproject.util.SocialUtil;

/**
 * @author MuhilKennedy
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	VerificationService verificationService;
	
	@Autowired
	private SocialUtil socialUtil;

	@Override
	public User findActiveUser(String email) throws Exception {
		return userRepository.findActiveUser(email, true);
	}

	@Override
	public User findUser(String email) throws Exception {
		return userRepository.findUser(email);
	}

	@Override
	@Transactional
	public void saveUser(User user) throws Exception {
		userRepository.save(user);
	}

	@Override
	public User loginUser(String email, String password) throws Exception {
		GenericResponse<User> response = new GenericResponse<>();
		List<String> msg = new ArrayList<>();
		User user = userRepository.findUser(email);
		if(user != null && BCrypt.checkpw(password, user.getPassword())) {
			return ResponseUtil.cleanUpUserResponse(user);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean createUser(User user) {
		try {
			String encrptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(CommonUtil.saltRounds));
			user.setPassword(encrptedPassword);
			user.setVerification(new Verification(user, CommonUtil.generateRandomCode(), new Date()));
			saveUser(user);
			return true;
		} catch (Exception ex) {
			LogUtil.getLogger(LoginServiceImpl.class).error("createUser : " + ex);
			return false;
		}
	}

	@Override
	public void sendVerification(String emailId, String code, String url) {
		String htmlBody = socialUtil.verificationEmailHTMLBody(code,
				socialUtil.generateVerificationURL(url, code, emailId));
		List<String> emailList = new ArrayList<String>(Arrays.asList(emailId));
		// Should be threaded in future change.
		socialUtil.sendEmail(emailList, "Verify your Account", htmlBody, null);
	}

	@Override
	@Transactional
	public boolean verifyUser(String email, String code) {
		try {
			User user = findUser(email);
			if (user != null && code.equals(user.getVerification().getCode())) {
				user.setActive(true);
				return true;
			}
		} catch (Exception e) {
			logger.error("verifyUser :: Exception during verifying user - " + e.getMessage());
		}
		return false;
	}
}
