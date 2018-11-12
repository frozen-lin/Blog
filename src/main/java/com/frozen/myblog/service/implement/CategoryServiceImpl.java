package com.frozen.myblog.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.ArticleMapper;
import com.frozen.myblog.mapper.CategoryMapper;
import com.frozen.myblog.pojo.Category;
import com.frozen.myblog.service.CategoryService;
import com.frozen.myblog.util.BlogException;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper cm;
	@Autowired
	private ArticleMapper am;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> rt;

	// 新增category
	@Override
	public void addCategory(Category category) throws BlogException {
		if (cm.queryIdByName(category.getCategoryName()) != null) {
			throw new BlogException("该文章分类已存在。");
		}
		saveCategoryIfNotExist();
		// 存进数据库后,category已查询到添加的ID
		cm.saveCategory(category);
		// redis存储新增category
		HashOperations<String, Integer, Category> ops = rt.opsForHash();
		// 将category_id作为hashkey
		ops.put("categoryHash", category.getCategoryId(), category);
	}

	// 查询category及对应的文章数量
	@Override
	public List<Category> queryCategoryAndArticleCount() {
		// 查询categorylist
		List<Category> categorylist = this.queryCategoryList();
		// 查询分类下的文章数量
		for (Category c : categorylist) {
			c.setCount(am.queryCountByCategory(c.getCategoryId()));
		}
		return categorylist;
	}

	// 删除
	@Override
	public void deleteCategory(Integer categoryId) throws BlogException {
		if (am.queryCountByCategory(categoryId) > 0) {
			throw new BlogException("该文章分类下还有文章，无法删除。");
		}
		cm.deleteCategoryByID(categoryId);
		rt.opsForHash().delete("categoryHash", categoryId);
	}

	// 修改
	@Override
	public void editCategory(Category category) {
		cm.updateCategory(category);
		HashOperations<String, Integer, Category> ops = rt.opsForHash();
		// 如果存在该ID,进行覆盖操作,否则不操作
		if (ops.hasKey("categoryHash", category.getCategoryId())) {
			ops.put("categoryHash", category.getCategoryId(), category);
		}
	}

	@Override
	public List<Category> queryCategoryList() {
		HashOperations<String, Integer, Category> ops = rt.opsForHash();
		// 不存在categoryHash,查询数据库,并存进redis
		saveCategoryIfNotExist();
		// 从categoryhash取出
		List<Category> categorylist = ops.values("categoryHash");
		return categorylist;
	}

	@Override
	public String queryCategoryNameById(Integer categoryId) {
		saveCategoryIfNotExist();
		HashOperations<String, Integer, Category> ops = rt.opsForHash();
		return ops.get("categoryHash", categoryId).getCategoryName();
	}

	// 检查是否存在categoryHash,不存在则存储
	private void saveCategoryIfNotExist() {
		if (!rt.hasKey("categoryHash")) {
			Map<Integer, Category> map = new HashMap<Integer, Category>();
			// 封装categorylist加入map操作
			for (Category c : cm.queryCategoryList()) {
				map.put(c.getCategoryId(), c);
			}
			rt.opsForHash().putAll("categoryHash", map);
		}
	}
}
