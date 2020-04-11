package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	String findCategoryQuery = "select cat from Category cat where cat.cId = :cId";
	String findParentCategoryQuery = "select cat from Category cat where cat.parentId is null";
	String findCategoryByNameQuery = "select cat from Category cat where cat.category = :category";
	String findChildrenQuery = "select cat from Category cat where cat.parentId = :parentId";
	
	@Query(findCategoryQuery)
	Category findCategory(@Param("cId") int cId);
	
	@Query(findParentCategoryQuery)
	List<Category> findParentCategories();
	
	@Query(findCategoryByNameQuery)
	Category findCategoryByName(@Param("category") String category);
	
	@Query(findChildrenQuery)
	List<Category> findChildren(@Param("parentId") int parentId);
	
}
