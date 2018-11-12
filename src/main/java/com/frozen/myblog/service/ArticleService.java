package com.frozen.myblog.service;

import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.Page;

public interface ArticleService {
	public void saveArticle(Article article);

	public void deleteArticle(Integer articleId);

	public Article queryArticleById(Integer articleId);

	public void editArticle(Article article);

	Page<Article> queryArticlePageByCategory(Integer categoryId, Integer curr);
	
	public Page<Article> queryArticlePageByParameter(String q, Integer curr);

	public Article queryArticleDetail(Integer articleId) throws BlogException;

	public Integer queryArticleCount();
}
