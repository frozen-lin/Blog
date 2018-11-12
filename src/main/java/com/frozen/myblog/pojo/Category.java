package com.frozen.myblog.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Category implements Serializable, Comparable<Category> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer categoryId;
	private String categoryName;
	// 用于存储该分类下文章的数量,不被序列化,也不参与hashcode和equals
	// Json注解,字段为空时,不进行转换
	@JsonInclude(value = Include.NON_NULL)
	private transient Integer count;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Category c) {
		if (this.categoryId == c.categoryId)
			return 0;
		if (c.categoryId == null)
			return -1;
		if (this.categoryId == null)
			return 1;
		return this.getCategoryId() - c.getCategoryId();
	}
}
