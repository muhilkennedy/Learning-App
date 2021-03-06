package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	String findActiveUserQuery = "select user from User user where user.emailId = :emailId and user.active = :active";
	String findUserQuery = "select user from User user where user.emailId = :emailId";

	@Query(findActiveUserQuery)
	User findActiveUser(@Param("emailId") String emailId, @Param("active") boolean active);
	
	@Query(findUserQuery)
	User findUser(@Param("emailId") String emailId);
	
}
