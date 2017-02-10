package com.retrofit_test.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 转让记录 - 可转让
 * 
 * @author droxy
 *
 */
public class ClaimsRecordKzr {

	private Date claimsCreateTime;
	private String title;
	private String isNewPerson;
	private BigDecimal nowClaimsAmount;
	private int claimsId;
	private YesNo isHx;// 是否华兴托管标

	public YesNo getIsHx() {
		return isHx;
	}

	public void setIsHx(YesNo isHx) {
		this.isHx = isHx;
	}
	public ClaimsRecordKzr() {
	}

	public ClaimsRecordKzr(Date claimsCreateTime, String title, String isNewPerson, BigDecimal nowClaimsAmount, int claimsId) {
		this.claimsCreateTime = claimsCreateTime;
		this.title = title;
		this.isNewPerson = isNewPerson;
		this.nowClaimsAmount = nowClaimsAmount;
		this.claimsId = claimsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsNewPerson() {
		return isNewPerson;
	}

	public void setIsNewPerson(String isNewPerson) {
		this.isNewPerson = isNewPerson;
	}

	public BigDecimal getNowClaimsAmount() {
		return nowClaimsAmount;
	}

	public void setNowClaimsAmount(BigDecimal nowClaimsAmount) {
		this.nowClaimsAmount = nowClaimsAmount;
	}

	public int getClaimsId() {
		return claimsId;
	}

	public void setClaimsId(int claimsId) {
		this.claimsId = claimsId;
	}

	public Date getClaimsCreateTime() {
		return claimsCreateTime;
	}

	public void setClaimsCreateTime(Date claimsCreateTime) {
		this.claimsCreateTime = claimsCreateTime;
	}

}
