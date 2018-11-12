package com.frozen.myblog.mapper;

import java.util.List;

import com.frozen.myblog.pojo.Carousel;

public interface CarouselMapper {

	void save(Carousel carousel);

	List<Carousel> queryCarouselList();

	void updateCarousel(Carousel carousel);

	void deleteCarouselById(Integer carouselId);

}
