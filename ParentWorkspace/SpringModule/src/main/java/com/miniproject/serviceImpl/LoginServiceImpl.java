package com.miniproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.User;
import com.miniproject.repository.UserRepository;
import com.miniproject.service.LoginService;

/**
 * @author MuhilKennedy
 *
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findActiveUser(String email) throws Exception {
		return userRepository.findUser(email, true);
	}

	@Override
	public User findUser(String email) throws Exception {
		return userRepository.findUser(email, false);
	}
	
	@Override
	public void saveUser(User user) throws Exception {
		userRepository.save(user);
	}

}
