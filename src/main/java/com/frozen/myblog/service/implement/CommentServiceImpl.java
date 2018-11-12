package com.frozen.myblog.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.CommentMapper;
import com.frozen.myblog.pojo.Comment;
import com.frozen.myblog.service.CommentService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.Page;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper cm;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> rt;

	@Override
	public void addComment(Comment comment) {
		// 保存评论
		saveCommentIfNotExist();
		cm.saveComment(comment);
		rt.opsForHash().put("commentHash", comment.getCommentId(), comment);
		// 将评论置于该article的redis list中
		//存入的评论和文章存入时保持同步,不存articleId
		Integer articleId = comment.getArticleId();
		comment.setArticleId(null);
		rt.opsForList().leftPush("article" + articleId, comment);

	}

	// 查个人评论
	@Override
	public List<Comment> queryCommentListByUsername(String username) {
		saveCommentIfNotExist();
		List<Integer> idlist = cm.queryCommentIdListByUserName(username);
		HashOperations<String, Integer, Comment> ops = rt.opsForHash();
		List<Comment> commentlist;
		commentlist = ops.multiGet("commentHash", idlist);
		return commentlist;
	}

	// 查最新评论
	@Override
	public List<Comment> queryNewCommentList() {
		saveCommentIfNotExist();
		List<Integer> idlist = cm.queryNewCommentId();
		HashOperations<String, Integer, Comment> ops = rt.opsForHash();
		List<Comment> commentlist = ops.multiGet("commentHash", idlist);
		return commentlist;
	}

	// 检查是否存在commentHash,不存在则存储
	private void saveCommentIfNotExist() {
		if (!rt.hasKey("commentHash")) {
			Map<Integer, Comment> map = new HashMap<Integer, Comment>();
			for (Comment c : cm.queryCommentList()) {
				map.put(c.getCommentId(), c);
			}
			rt.opsForHash().putAll("commentHash", map);
		}
	}

	@Override
	public void deleteCommentByIdByUser(Integer commentId, String username) throws BlogException {
		saveCommentIfNotExist();
		// 获取comment 便于 确认用户,以及删除以articleId为键的list中的评论
		HashOperations<String, Integer, Comment> ops = rt.opsForHash();
		Comment comment = ops.get("commentHash", commentId);
		//校验用户
		if (comment.getUsername().equals(username)) {
			deleteComment(comment);
		} else {
			throw new BlogException("你不是该评论的用户，无权限删除。");
		}
	}
	
	@Override
	public void deleteCommentByIdByAdmin(Integer commentId) {
		saveCommentIfNotExist();
		// 获取comment 便于 确认用户,以及删除以articleId为键的list中的评论
		HashOperations<String, Integer, Comment> ops = rt.opsForHash();
		Comment comment = ops.get("commentHash", commentId);
		deleteComment(comment);
	}
	
	private void deleteComment(Comment comment) {
		if(comment!=null) {
			// 数据库删除
			cm.deleteCommentById(comment.getCommentId());
			// redisHash删除
			rt.opsForHash().delete("commentHash",comment.getCommentId());
			// redislist删除,因为redislist中存的comment是没有articleId,所以将其置为null
			Integer articleId = comment.getArticleId();
			comment.setArticleId(null);
			rt.opsForList().remove("article" + articleId, 0, comment);
		}
	}
	
	
	@Override
	public Integer queryCommentCount() {
		saveCommentIfNotExist();
		return rt.opsForHash().size("commentHash").intValue();
	}
	
	//查找一页评论
	@Override
	public Page<Comment> queryCommentPage(Integer curr) {
		saveCommentIfNotExist();
		HashOperations<String,Integer,Comment> ops=rt.opsForHash();
		Integer total = ops.size("commentHash").intValue();
		Page<Comment> page = new Page<Comment>();
		//每页8条评论
		page.setLimit(8);
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		//装配查询参数
		parameterMap.put("start", (curr-1)*page.getLimit());
		parameterMap.put("limit", page.getLimit());
		//获取一页Id
		List<Integer> idlist = cm.queryPageCommentIdList(parameterMap);
		List<Comment> commentList=ops.multiGet("commentHash", idlist);
		//装配参数
		page.setCurr(curr);
		page.setList(commentList);
		page.setTotal(total);
		return page;
	}

}
