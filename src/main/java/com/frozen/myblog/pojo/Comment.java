package com.frozen.myblog.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Comment implements Serializable,Comparable<Comment>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//转化json时的不转化属性
	@JsonIgnore
	private Integer commentId;
	private String commentContent;
	@JsonIgnore
	private Date commentTime;
	private String username;
	@JsonIgnore
	private Integer articleId;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((commentContent == null) ? 0 : commentContent.hashCode());
		result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
		result = prime * result + ((commentTime == null) ? 0 : commentTime.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Comment other = (Comment) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (commentContent == null) {
			if (other.commentContent != null)
				return false;
		} else if (!commentContent.equals(other.commentContent))
			return false;
		if (commentId == null) {
			if (other.commentId != null)
				return false;
		} else if (!commentId.equals(other.commentId))
			return false;
		if (commentTime == null) {
			if (other.commentTime != null)
				return false;
		} else if (!commentTime.equals(other.commentTime))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", commentContent=" + commentContent + ", commentTime=" + commentTime
				+ ", username=" + username + ", articleId=" + articleId + "]";
	}

	@Override
	public int compareTo(Comment c) {
		//逆序排列
		return - this.getCommentTime().compareTo(c.getCommentTime());
	}

	
}
