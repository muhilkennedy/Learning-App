package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	String findItemQuery = "select item from Item item where item.itemId = :itemId";
	String findActiveItemQuery = "select item from Item item where item.itemId = :itemId and item.active=1";
	String findAllActiveItemsQuery = "select item from Item item where item.active=1";
	
	@Query(findItemQuery)
	Item findItem(@Param("itemId") int itemId);
	
	@Query(findActiveItemQuery)
	Item findActiveItem(@Param("itemId") int itemId);
	
	@Query(findAllActiveItemsQuery)
	List<Item> findAllActiveItems();
	
}
