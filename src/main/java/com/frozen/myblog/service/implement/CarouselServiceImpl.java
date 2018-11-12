package com.frozen.myblog.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.CarouselMapper;
import com.frozen.myblog.pojo.Carousel;
import com.frozen.myblog.service.CarouselService;
import com.frozen.myblog.util.MyUtils;

@Service
public class CarouselServiceImpl implements CarouselService {
	@Autowired
	CarouselMapper cm;
	@Resource(name="redisTemplate")
	RedisTemplate<String, Object> rt;

	@Override
	public void saveCarousel(Carousel carousel) {
		saveCarouselIfNotExist();
		cm.save(carousel);
		rt.opsForHash().putIfAbsent("carouselHash", carousel.getCarouselId(), carousel);
	}

	@Override
	public List<Carousel> queryCarousel() {
		saveCarouselIfNotExist();
		HashOperations<String, Integer,Carousel> ops = rt.opsForHash();
		return ops.values("carouselHash");
	}

	@Override
	public void editCarousel(Carousel carousel) {
		//数据库更新
		cm.updateCarousel(carousel);
		HashOperations<String, Integer,Carousel> ops = rt.opsForHash();
		Integer carouselId =carousel.getCarouselId();
		//若redis中存在该条数据,进行redis更新
		if(ops.hasKey("carouselHash",carouselId)) {
			Carousel oldCarousel = ops.get("carouselHash", carouselId);
			//把不是空值的字段赋给原始carousel
			if(!MyUtils.checkempty(carousel.getCarouselAuthor())) {
				oldCarousel.setCarouselAuthor(carousel.getCarouselAuthor());
			}
			if(!MyUtils.checkempty(carousel.getCarouselSign())) {
				oldCarousel.setCarouselSign(carousel.getCarouselSign());
			}
			if(carousel.getCarouselOrder()!=null) {
				oldCarousel.setCarouselOrder(carousel.getCarouselOrder());
			}
			//覆盖
			ops.put("carouselHash", carouselId, oldCarousel);
		}	
	}

	@Override
	public Carousel deleteCarousel(Integer carouselId) {
		cm.deleteCarouselById(carouselId);
		HashOperations<String,Integer,Carousel> ops=rt.opsForHash();
		//取出要删除的carousel,便于controller层删除图片。
		Carousel carousel = ops.get("carouselHash", carouselId);
		ops.delete("carouselHash", carouselId);
		return carousel;
	}

	// 检查是否存在carouselHash,不存在则存储
	private void saveCarouselIfNotExist() {
		if (!rt.hasKey("carouselHash")) {
			Map<Integer, Carousel> map = new HashMap<Integer, Carousel>();
			for (Carousel c : cm.queryCarouselList()) {
				map.put(c.getCarouselId(), c);
			}
			rt.opsForHash().putAll("carouselHash", map);
		}
	}
}
