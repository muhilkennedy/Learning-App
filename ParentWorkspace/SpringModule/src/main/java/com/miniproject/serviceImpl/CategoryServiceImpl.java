package com.miniproject.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Category;
import com.miniproject.repository.CategoryRepository;
import com.miniproject.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	@Transactional
	public void save(Category category) {
		categoryRepo.save(category);
	}

	@Override
	public Category find(int id) {
		return categoryRepo.findCategory(id);
	}
	
	@Override
	public Category findByName(String categoryName) {
		return categoryRepo.findCategoryByName(categoryName);
	}
	
	@Override
	public List<Category> findAllDeletedCategory(){
		return categoryRepo.findDeletedCategory();
	}
	
	@Override
	public List<Category> findAllActiveCategory(){
		return categoryRepo.findActiveCategory();
	}
	
	@Override
	public List<Category> findAllCategoryMarkedForDeletion(){
		return categoryRepo.findMarkedForDeletion();
	}

	@Override
	public List<Category> findChildren(int id) {
		return categoryRepo.findChildren(id);
	}
	
	@Override
	public Category createCategory(Category cat) throws Exception {
		logger.debug("createCategory :: Creating category - " + cat.getCategory());
		if (cat.getParentId() != null) {
			Category parentCategory = categoryRepo.findCategory(cat.getParentId());
			if (parentCategory == null) {
				throw new Exception("Parent not found");
			}
		}
		categoryRepo.save(cat);
		return cat;
	}
	
	private Map getExplodedContentRecursive(List<Category> categories) throws JSONException {
		Map map = new HashMap();
		for(Category category : categories) {
			map.put(category.getCategory() , getExplodedContentRecursive(findChildren(category.getcId())));
		}
		return map;
	}
	
	private List<Integer> getCategoryIdRecursive(Category category) throws Exception {
		List<Integer> catChildren = new ArrayList<>();
		catChildren.add(category.getcId());
		for(Category cat: findChildren(category.getcId())) {
			catChildren.addAll(getCategoryIdRecursive(cat));
		}
		return catChildren;
	}
	
	@Override
	public Map buildTreeMap() throws Exception{
		logger.debug("buildTreeMap :: Get all categories recursively");
		List<Category> baseParents = categoryRepo.findParentCategories();
		return  getExplodedContentRecursive(baseParents);
	}
	
	@Override
	public Map findChildrenRecursive(int parentId) throws Exception {
		logger.debug("findChildrenRecursive :: Getting children for parent - " + parentId);
		List<Category> baseParents = categoryRepo.findChildren(parentId);
		return  getExplodedContentRecursive(baseParents);
	}
	
	@Override 
	public List findChildrenIdRecursive(Category cat) throws Exception {
		return getCategoryIdRecursive(cat);
	}
	
	/*
	 * Only soft delete will be done to the category and all its children.
	 */
	@Override
	@Transactional
	public void deleteCategory(Category cat, boolean deleteChildren) throws Exception {
		if(deleteChildren) {
			List<Integer> children = findChildrenIdRecursive(cat);
			children.stream().forEach(child -> {
				Category category = find(child);
				category.setDeleted(true);
				category.setMarkForDelete(false);
			});
		}
		else {
			cat.setDeleted(true);
			cat.setMarkForDelete(false);
		}
	}
	
	/*
	 * This enables scheduled task to pick up the category for deletion in next run.
	 */
	@Override
	@Transactional
	public void markCategoryForDeletion(Category cat, boolean markChildren) throws Exception {
		if(markChildren) {
			List<Integer> children = findChildrenIdRecursive(cat);
			children.stream().forEach(child -> {
				Category category = find(child);
				category.setMarkForDelete(true);
			});
		}
		else {
			cat.setMarkForDelete(true);
		}
	}

}
