package com.miniproject.service;

import org.springframework.stereotype.Service;

import com.miniproject.model.User;

@Service
public interface LoginService {
	User findActiveUser(String email) throws Exception;
	User findUser(String email) throws Exception;
	void saveUser(User user) throws Exception;
}
