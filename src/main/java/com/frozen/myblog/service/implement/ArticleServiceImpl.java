package com.frozen.myblog.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.ArticleMapper;
import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.pojo.Category;
import com.frozen.myblog.pojo.Comment;
import com.frozen.myblog.service.ArticleService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.Page;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapper am;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, ? extends Object> rt;

	@Override
	public void saveArticle(Article article) {
		saveArticleIfNotExist();
		am.saveArticle(article);
		//获取分类名字
		HashOperations<String, Integer, Category> ops = rt.opsForHash();
		String categoryName = ops.get("categoryHash", article.getCategory().getCategoryId()).getCategoryName();
		article.getCategory().setCategoryName(categoryName);
		//redis存储
		rt.opsForHash().put("articleHash", article.getArticleId(), article);
	}

	@Override
	public void deleteArticle(Integer articleId) {
		am.deleteArticleById(articleId);
		rt.opsForHash().delete("articleHash", articleId);
	}

	// 查询单篇文章所有信息
	@Override
	public Article queryArticleById(Integer articleId) {
		saveArticleIfNotExist();
		Article article = (Article) rt.opsForHash().get("articleHash", articleId);
		return article;
	}

	@Override
	public void editArticle(Article article) {
		am.updateArticle(article);
		if (rt.opsForHash().hasKey("articleHash", article.getArticleId())) {
			//获取分类名字
			HashOperations<String, Integer, Category> ops = rt.opsForHash();
			String categoryName = ops.get("categoryHash", article.getCategory().getCategoryId()).getCategoryName();
			article.getCategory().setCategoryName(categoryName);
			//存入
			rt.opsForHash().put("articleHash", article.getArticleId(), article);
		}

	}

	// 按分类查找文章并分页
	@Override
	public Page<Article> queryArticlePageByCategory(Integer categoryId, Integer curr) {
		// 若redis中articleHash不存在,先进行存储
		saveArticleIfNotExist();
		Integer total;
		HashOperations<String, Integer, Article> opsForHash = rt.opsForHash();
		// 若categoryId=0,查询所有文章
		if (categoryId.equals(0)) {
			total = opsForHash.size("articleHash").intValue();
		} else {
			total = am.queryCountByCategory(categoryId);
		}
		Page<Article> articlePage = new Page<Article>();
		articlePage.setCurr(curr);
		articlePage.setTotal(total);
		// 装配查询参数
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("categoryId", categoryId);
		parameterMap.put("start", (curr - 1) * articlePage.getLimit());
		parameterMap.put("limit", articlePage.getLimit());
		pageHelp(parameterMap, articlePage);
		return articlePage;
	}

	// 根据查询条件查找article
	@Override
	public Page<Article> queryArticlePageByParameter(String q, Integer curr) {
		saveArticleIfNotExist();
		Integer total = am.queryCountByParameter("%" + q + "%");
		Page<Article> articlePage = new Page<Article>();
		articlePage.setTotal(total);
		articlePage.setCurr(curr);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		// 装配查询参数
		// 模糊查询
		parameterMap.put("q", "%" + q + "%");
		parameterMap.put("start", (curr - 1) * articlePage.getLimit());
		parameterMap.put("limit", articlePage.getLimit());
		pageHelp(parameterMap, articlePage);
		return articlePage;
	}

	// 封装操作
	private void pageHelp(Map<String, Object> parameterMap, Page<Article> articlePage) {
		// 查询
		List<Integer> idList = am.queryArticlePage(parameterMap);
		HashOperations<String, Integer, Article> ops = rt.opsForHash();
		List<Article> list = ops.multiGet("articleHash", idList);
		// 查评论数量
		for (Article a : list) {
			a.setCommentCount(rt.opsForList().size("article" + a.getArticleId()).intValue());
		}
		// 装备Page
		articlePage.setList(list);
		// 计算总页数
		articlePage.calculatePageTotal();

	}

	// 查询单篇文章信息及上下篇
	@Override
	public Article queryArticleDetail(Integer articleId) throws BlogException {
		Article article = this.queryArticleById(articleId);
		if (article == null) {
			throw new BlogException("文章Id不存在");
		}
		// 查找评论
		@SuppressWarnings("unchecked")
		List<Comment> list = (List<Comment>) rt.opsForList().range("article" + articleId, 0, -1);
		// 查找上一篇,下一篇
		Article pre = am.queryPreArticle(article.getArticleId());
		Article next = am.queryNextArticle(article.getArticleId());
		// 装配
		article.setCommentList(list);
		article.setPre(pre);
		article.setNext(next);
		return article;
	}

	// 检查是否存在articleHash,不存在则存储
	private void saveArticleIfNotExist() {
		if (!rt.hasKey("articleHash")) {
			Map<Integer, Article> map = new HashMap<Integer, Article>();
			// 封装articlelist加入map操作
			@SuppressWarnings("unchecked") // 强转避免warning
			ListOperations<String, Comment> listops = (ListOperations<String, Comment>) rt.opsForList();
			for (Article a : am.queryArticleList()) {
				// 按文章存储评论
				//该查出的评论不查articleId,不然每篇文章都有一个保存articleId的空comment
				List<Comment> list = a.getCommentList();
				// 判断评论不为空
				if (list != null && list.size() != 0) {
					listops.leftPushAll("article" + a.getArticleId(), a.getCommentList());
				}
				map.put(a.getArticleId(), a);
			}
			rt.opsForHash().putAll("articleHash", map);
		}
	}

	// 查找文章总数量
	@Override
	public Integer queryArticleCount() {
		saveArticleIfNotExist();
		return rt.opsForHash().size("articleHash").intValue();
	}

}
