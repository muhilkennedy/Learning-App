package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Item;

@Repository
public interface CartRepository extends JpaRepository<Item, Integer>{

}
