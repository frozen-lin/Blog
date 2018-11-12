package com.frozen.myblog.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer articleId;
	private String articleTitle;
	private String articleBrief;
	private String articleContent;
	private Date articleTime;
	private Category category;

	// 不进行序列化,否则在list上添加删除回复,会出现线程安全问题
	private transient List<Comment> commentList;
	// 用于存储评论数量,不进行序列化
	private transient Integer commentCount;

	// 用于存放上一篇和下一篇文章信息,不进行序列化
	private transient Article pre;
	private transient Article next;

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleBrief() {
		return articleBrief;
	}

	public void setArticleBrief(String articleBrief) {
		this.articleBrief = articleBrief;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public Date getArticleTime() {
		return articleTime;
	}

	public void setArticleTime(Date articleTime) {
		this.articleTime = articleTime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleBrief == null) ? 0 : articleBrief.hashCode());
		result = prime * result + ((articleContent == null) ? 0 : articleContent.hashCode());
		result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((articleTime == null) ? 0 : articleTime.hashCode());
		result = prime * result + ((articleTitle == null) ? 0 : articleTitle.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
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
		Article other = (Article) obj;
		if (articleBrief == null) {
			if (other.articleBrief != null)
				return false;
		} else if (!articleBrief.equals(other.articleBrief))
			return false;
		if (articleContent == null) {
			if (other.articleContent != null)
				return false;
		} else if (!articleContent.equals(other.articleContent))
			return false;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (articleTime == null) {
			if (other.articleTime != null)
				return false;
		} else if (!articleTime.equals(other.articleTime))
			return false;
		if (articleTitle == null) {
			if (other.articleTitle != null)
				return false;
		} else if (!articleTitle.equals(other.articleTitle))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", articleTitle=" + articleTitle + ", articleBrief=" + articleBrief
				+ ", articleContent=" + articleContent + ", articleTime=" + articleTime + ", category=" + category
				+ "]";
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public Article getPre() {
		return pre;
	}

	public void setPre(Article pre) {
		this.pre = pre;
	}

	public Article getNext() {
		return next;
	}

	public void setNext(Article next) {
		this.next = next;
	}

}
