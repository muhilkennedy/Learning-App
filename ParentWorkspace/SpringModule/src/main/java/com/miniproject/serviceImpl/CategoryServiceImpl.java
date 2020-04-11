package com.miniproject.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Category> findChildren(int id) {
		return categoryRepo.findChildren(id);
	}
	
	private Map getExplodedContentRecursive(List<Category> categories) throws JSONException {
		Map test = new HashMap();
		for(Category category : categories) {
			test.put(category.getCategory() , getExplodedContentRecursive(findChildren(category.getcId())));
		}
		return test;
	}
	
	@Override
	public Map buildTreeMap() throws Exception{
		List<Category> baseParents = categoryRepo.findParentCategories();
		return  getExplodedContentRecursive(baseParents);
	}
	
	@Override
	public Map findChildrenRecursive(int parentId) throws Exception {
		List<Category> baseParents = categoryRepo.findChildren(parentId);
		return  getExplodedContentRecursive(baseParents);
	}

}
