package com.miniproject.serviceImpl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.model.User;
import com.miniproject.repository.UserRepository;
import com.miniproject.service.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<User> user = userRepository.findById((long) 1);
//		User user = userRepository.find(email);
//		if(user == null) {
//			throw new UsernameNotFoundException("user does not exist");
//		}
//		
//		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE " + user.getRole());
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmailId(),
//				user.getPassword(), Arrays.asList(authority));
//		
//		return userDetails;
		return null;
	}

}
