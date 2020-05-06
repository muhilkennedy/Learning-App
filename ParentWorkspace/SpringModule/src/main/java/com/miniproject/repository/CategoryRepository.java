package com.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	String findCategoryQuery = "select cat from Category cat where cat.cId = :cId and cat.isDeleted=0";
	String findParentCategoryQuery = "select cat from Category cat where cat.parentId is null and cat.isDeleted=0";
	String findCategoryByNameQuery = "select cat from Category cat where cat.category = :category and cat.isDeleted=0";
	String findChildrenQuery = "select cat from Category cat where cat.parentId = :parentId and cat.isDeleted=0";
	String findDeletedCategoryQuery = "select cat from Category cat where cat.isDeleted=1"; 
	String findActiveCategoryQuery = "select cat from Category cat where cat.isDeleted=0";
	String findMarkedForDeletionQuery = "select cat from Category cat where cat.markForDelete=1"; 
	
	@Query(findCategoryQuery)
	Category findCategory(@Param("cId") int cId);
	
	@Query(findParentCategoryQuery)
	List<Category> findParentCategories();
	
	@Query(findCategoryByNameQuery)
	Category findCategoryByName(@Param("category") String category);
	
	@Query(findChildrenQuery)
	List<Category> findChildren(@Param("parentId") int parentId);
	
	@Query(findDeletedCategoryQuery)
	List<Category> findDeletedCategory();
	
	@Query(findActiveCategoryQuery)
	List<Category> findActiveCategory();
	
	@Query(findMarkedForDeletionQuery)
	List<Category> findMarkedForDeletion();
	
}
