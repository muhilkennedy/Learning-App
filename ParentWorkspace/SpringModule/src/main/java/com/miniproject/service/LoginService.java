package com.miniproject.service;

import org.springframework.stereotype.Service;

import com.miniproject.messages.GenericResponse;
import com.miniproject.model.User;

@Service
public interface LoginService {
	User findActiveUser(String email) throws Exception;
	User findUser(String email) throws Exception;
	void saveUser(User user) throws Exception;
	User loginUser(String email, String password) throws Exception;
	boolean createUser(User user);
	void sendVerification(String emailId, String code, String string);
	boolean verifyUser(String email, String code);
}
