package com.frozen.myblog.pojo;

import java.io.Serializable;

public class Carousel implements Serializable, Comparable<Carousel> {

	private static final long serialVersionUID = 1L;
	private Integer carouselId;
	private String carouselUrl;
	private String carouselAuthor;
	private String carouselSign;
	private Integer carouselOrder;

	public Integer getCarouselId() {
		return carouselId;
	}

	public void setCarouselId(Integer carouselId) {
		this.carouselId = carouselId;
	}

	public String getCarouselAuthor() {
		return carouselAuthor;
	}

	public void setCarouselAuthor(String carouselAuthor) {
		this.carouselAuthor = carouselAuthor;
	}

	public String getCarouselSign() {
		return carouselSign;
	}

	public void setCarouselSign(String carouselSign) {
		this.carouselSign = carouselSign;
	}

	public Integer getCarouselOrder() {
		return carouselOrder;
	}

	public void setCarouselOrder(Integer carouselOrder) {
		this.carouselOrder = carouselOrder;
	}

	public String getCarouselUrl() {
		return carouselUrl;
	}

	public void setCarouselUrl(String carouselUrl) {
		this.carouselUrl = carouselUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carouselAuthor == null) ? 0 : carouselAuthor.hashCode());
		result = prime * result + ((carouselId == null) ? 0 : carouselId.hashCode());
		result = prime * result + ((carouselOrder == null) ? 0 : carouselOrder.hashCode());
		result = prime * result + ((carouselSign == null) ? 0 : carouselSign.hashCode());
		result = prime * result + ((carouselUrl == null) ? 0 : carouselUrl.hashCode());
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
		Carousel other = (Carousel) obj;
		if (carouselAuthor == null) {
			if (other.carouselAuthor != null)
				return false;
		} else if (!carouselAuthor.equals(other.carouselAuthor))
			return false;
		if (carouselId == null) {
			if (other.carouselId != null)
				return false;
		} else if (!carouselId.equals(other.carouselId))
			return false;
		if (carouselOrder == null) {
			if (other.carouselOrder != null)
				return false;
		} else if (!carouselOrder.equals(other.carouselOrder))
			return false;
		if (carouselSign == null) {
			if (other.carouselSign != null)
				return false;
		} else if (!carouselSign.equals(other.carouselSign))
			return false;
		if (carouselUrl == null) {
			if (other.carouselUrl != null)
				return false;
		} else if (!carouselUrl.equals(other.carouselUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Carousel [carouselId=" + carouselId + ", carouselUrl=" + carouselUrl + ", carouselAuthor="
				+ carouselAuthor + ", carouselSign=" + carouselSign + ", carouselOrder=" + carouselOrder + "]";
	}

	@Override
	public int compareTo(Carousel c) {
		if (this.carouselOrder == c.carouselOrder) {
			return 0;
		}
		if (c.carouselOrder == null) {
			return -1;
		}
		if (this.carouselOrder == null) {
			return 1;
		}
		return this.carouselOrder - c.carouselOrder;
	}
}
