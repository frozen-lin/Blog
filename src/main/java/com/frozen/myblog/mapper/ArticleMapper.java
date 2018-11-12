package com.frozen.myblog.mapper;

import java.util.List;
import java.util.Map;

import com.frozen.myblog.pojo.Article;

public interface ArticleMapper {
	void saveArticle(Article article);

	Integer queryCountByCategory(Integer categoryId);

	List<Article> queryArticleList();

	List<Integer> queryArticlePage(Map<String, Object> parameterMap);

	void deleteArticleById(Integer articleId);

	void updateArticle(Article article);

	Integer queryCountByParameter(String q);

	Article queryPreArticle(Integer articleId);

	Article queryNextArticle(Integer articleId);

}
