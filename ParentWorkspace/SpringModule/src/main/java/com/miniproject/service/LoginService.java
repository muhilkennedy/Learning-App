package com.miniproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.miniproject.model.Cart;
import com.miniproject.model.User;

@Service
public interface LoginService {
	User findActiveUser(String email) throws Exception;
	User findUser(String email) throws Exception;
	void saveUser(User user) throws Exception;
	User loginUser(String email, String password) throws Exception;
	boolean createUser(User user);
	void sendVerification(String emailId, String code, String string, boolean onlyCode);
	boolean verifyUser(String email, String code);
	void createUserVerification(User user) throws Exception;
	void updateUserPassword(User user, String password) throws Exception;
	User insertCartToUser(User user, List<Cart> cartItems) throws Exception;
	Map<Integer, Integer> getCartForUser(int id) throws Exception;
}
