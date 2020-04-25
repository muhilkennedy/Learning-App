package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	String findItemQuery = "select item from Item item where item.itemId = :itemId";
	
	@Query(findItemQuery)
	Item findItem(@Param("itemId") int itemId);
	
}
