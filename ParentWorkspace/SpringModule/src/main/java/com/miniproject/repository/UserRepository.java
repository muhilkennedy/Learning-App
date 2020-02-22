package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

//    public static String FindQuery = "select u from userinfo u where u.userid = 1 and u.emailid = :email";
//    @Query(FindQuery)
//    User finduser (@Param("email") String email);
	
	
//	public static String FindQuery = "select u from user u where u.userid = 1";
//    @Query(FindQuery)
//    Optional<User> finduser ();
    
}
