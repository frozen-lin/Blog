package com.frozen.myblog.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.LinkMapper;
import com.frozen.myblog.pojo.Link;
import com.frozen.myblog.service.LinkService;

@Service()
public class LinkServiceImpl implements LinkService {
	@Autowired
	private LinkMapper lm;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> rt;

	@Override
	public List<Link> queryLink() {
		saveLinkIfNotExist();
		HashOperations<String, Integer, Link> ops = rt.opsForHash();
		List<Link> linkList = ops.values("linkHash");
		return linkList;
	}

	@Override
	public void editLink(Link link) {
		lm.updateLink(link);
		if (rt.opsForHash().hasKey("linkHash", link.getLinkId())) {
			rt.opsForHash().put("linkHash", link.getLinkId(), link);
		}

	}

	@Override
	public void deleteLink(Integer linkId) {
		lm.deleteLinkById(linkId);
		if (rt.opsForHash().hasKey("linkHash", linkId)) {
			rt.opsForHash().delete("linkHash", linkId);
		}
	}

	@Override
	public void addLink(Link link) {
		saveLinkIfNotExist();
		lm.addLink(link);
		rt.opsForHash().put("linkHash", link.getLinkId(), link);
		;

	}

	// 检查是否存在linkHash,不存在则存储
	private void saveLinkIfNotExist() {
		if (!rt.hasKey("linkHash")) {
			Map<Integer, Link> map = new HashMap<Integer, Link>();
			for (Link l : lm.queryLinkList()) {
				map.put(l.getLinkId(), l);
			}
			rt.opsForHash().putAll("linkHash", map);
		}
	}

	@Override
	public Integer queryLinkCount() {
		saveLinkIfNotExist();
		return rt.opsForHash().size("linkHash").intValue();
	}

}
