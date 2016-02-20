package com.xidian.activity;

import java.io.Serializable;
import java.util.Date;

public class RankerJson implements Serializable{

	private Integer id;
	
	private String auserImgUrl;
	
	private String buserImgUrl;
	
	private Integer matchs;
	
	private Integer followers;
	
	private Date    createAt;

	public RankerJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RankerJson(Integer id, String auserImgUrl, String buserImgUrl, Integer matchs, Integer followers,
			Date createAt) {
		super();
		this.id = id;
		this.auserImgUrl = auserImgUrl;
		this.buserImgUrl = buserImgUrl;
		this.matchs = matchs;
		this.followers = followers;
		this.createAt = createAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuserImgUrl() {
		return auserImgUrl;
	}

	public void setAuserImgUrl(String auserImgUrl) {
		this.auserImgUrl = auserImgUrl;
	}

	public String getBuserImgUrl() {
		return buserImgUrl;
	}

	public void setBuserImgUrl(String buserImgUrl) {
		this.buserImgUrl = buserImgUrl;
	}

	public Integer getMatchs() {
		return matchs;
	}

	public void setMatchs(Integer matchs) {
		this.matchs = matchs;
	}

	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "RankerJson [id=" + id + ", auserImgUrl=" + auserImgUrl + ", buserImgUrl=" + buserImgUrl + ", matchs="
				+ matchs + ", followers=" + followers + ", createAt=" + createAt + "]";
	}
	
}
