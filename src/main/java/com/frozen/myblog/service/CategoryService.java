package com.frozen.myblog.service;

import java.util.List;

import com.frozen.myblog.pojo.Category;
import com.frozen.myblog.util.BlogException;

public interface CategoryService {

	void addCategory(Category category) throws BlogException;

	List<Category> queryCategoryAndArticleCount();

	void deleteCategory(Integer categoryId) throws BlogException;

	void editCategory(Category category);

	List<Category> queryCategoryList();

	String queryCategoryNameById(Integer categoryId);

}
