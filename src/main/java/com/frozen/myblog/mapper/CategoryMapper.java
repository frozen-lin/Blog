package com.frozen.myblog.mapper;

import java.util.List;

import com.frozen.myblog.pojo.Category;

public interface CategoryMapper {

	Integer queryIdByName(String categoryName);

	void saveCategory(Category category);

	List<Category> queryCategoryList();

	void deleteCategoryByID(Integer categoryId);

	void updateCategory(Category category);
}
