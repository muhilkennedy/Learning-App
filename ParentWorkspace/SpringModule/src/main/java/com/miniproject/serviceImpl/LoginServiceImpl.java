package com.miniproject.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.messages.GenericResponse;
import com.miniproject.messages.Response;
import com.miniproject.model.User;
import com.miniproject.model.Verification;
import com.miniproject.repository.UserRepository;
import com.miniproject.service.LoginService;
import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;
import com.miniproject.util.ResponseUtil;

/**
 * @author MuhilKennedy
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

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
	public GenericResponse<User> loginUser(String email, String password) throws Exception {
		GenericResponse<User> response = new GenericResponse<>();
		List<String> msg = new ArrayList<>();
		User user = userRepository.findUser(email);
		if (user == null) {
			msg.add("User Does Not Exist");
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.NOT_FOUND);
		} else if (BCrypt.checkpw(password, user.getPassword())) {
			user.setPassword(null);
			response.setStatus(Response.Status.OK);
			response.setData(ResponseUtil.cleanUpUserResponse(user));
		} else {
			msg.add("Password is Incorrect");
			response.setErrorMessages(msg);
			response.setStatus(Response.Status.FORBIDDEN);
		}
		return response;
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

}
