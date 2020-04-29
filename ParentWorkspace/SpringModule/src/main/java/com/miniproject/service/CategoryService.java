package com.miniproject.service;

import java.util.List;
import java.util.Map;

import com.miniproject.model.Category;

public interface CategoryService {

	void save(Category category);
	
	Category find(int id);
	
	List<Category> findChildren(int id);

	Category findByName(String categoryName);

	Map buildTreeMap() throws Exception;

	Map findChildrenRecursive(int parentId) throws Exception;

	Category createCategory(Category cat) throws Exception;

	List findChildrenIdRecursive(Category cat) throws Exception;

	List<Category> findAllDeletedCategory();

	List<Category> findAllActiveCategory();

	List<Category> findAllCategoryMarkedForDeletion();

	void deleteCategory(Category cat, boolean deleteChildren) throws Exception;

	void markCategoryForDeletion(Category cat, boolean markChildren) throws Exception;

}
