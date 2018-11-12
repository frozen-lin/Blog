package com.frozen.myblog.mapper;

import java.util.List;
import java.util.Map;

import com.frozen.myblog.pojo.Comment;

public interface CommentMapper {

	void saveComment(Comment comment);

	List<Comment> queryCommentList();

	List<Integer> queryCommentIdListByUserName(String username);

	List<Integer> queryNewCommentId();

	void deleteCommentById(Integer commentId);

	List<Integer> queryPageCommentIdList(Map<String, Object> parameterMap);

}
