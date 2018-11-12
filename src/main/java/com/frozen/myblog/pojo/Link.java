package com.frozen.myblog.pojo;

import java.io.Serializable;

public class Link implements Serializable, Comparable<Link> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer linkId;
	private String link;
	private String linkName;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((linkId == null) ? 0 : linkId.hashCode());
		result = prime * result + ((linkName == null) ? 0 : linkName.hashCode());
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
		Link other = (Link) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (linkId == null) {
			if (other.linkId != null)
				return false;
		} else if (!linkId.equals(other.linkId))
			return false;
		if (linkName == null) {
			if (other.linkName != null)
				return false;
		} else if (!linkName.equals(other.linkName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Link [linkId=" + linkId + ", link=" + link + ", linkName=" + linkName + "]";
	}

	@Override
	public int compareTo(Link i) {
		if (this.linkId == i.linkId)
			return 0;
		if (i.linkId == null)
			return -1;
		if (this.linkId == null)
			return 1;
		return this.linkId - i.linkId;
	}

}
