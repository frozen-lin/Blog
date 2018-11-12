package com.frozen.myblog.service;

import java.util.List;

import com.frozen.myblog.pojo.Carousel;

public interface CarouselService {

	void saveCarousel(Carousel carousel);

	List<Carousel> queryCarousel();

	void editCarousel(Carousel carousel);

	Carousel deleteCarousel(Integer carouselId);

}
