package com.frozen.myblog.service;

import java.util.List;

import com.frozen.myblog.pojo.Comment;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.Page;

public interface CommentService {

	void addComment(Comment comment);

	List<Comment> queryCommentListByUsername(String username);

	List<Comment> queryNewCommentList();

	void deleteCommentByIdByUser(Integer commentId,String username) throws BlogException;

	Integer queryCommentCount();

	Page<Comment> queryCommentPage(Integer curr);

	void deleteCommentByIdByAdmin(Integer commentId);

}
