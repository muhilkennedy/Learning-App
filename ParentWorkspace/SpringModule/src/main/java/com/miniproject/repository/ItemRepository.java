package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	String findItemQuery ="select item from Item item where item.ITEMID=:itemId";
	String findItembyCategoryQuery ="select item from Item item where item.CID=:cId";
	String findItembyItemNameQuery ="select item from Item item where item.ITEM=:item";
	String findItembyBrandNameQuery ="select item from Item item where item.BRANDNAME=:brand";
	
	@Query(findItemQuery)
	Item findItem(@Param("itemId") int itemId);
	
	@Query(findItembyCategoryQuery)
	List<Item> findItemByCategory(@Param("cId") int cId);

	@Query(findItembyItemNameQuery)
	List<Item> findItembyItemName(@Param("item") String item);
	
	@Query(findItembyBrandNameQuery)
	List<Item> findItembyBrandName(@Param("brand") String brand);
}
