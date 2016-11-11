package com.koch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="dug_coin_info")
public class DugCoinInfo extends BaseEntity{ 
	private static final long serialVersionUID = 6300123837324988951L;
	
	private Member member;
	private Integer score;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Column
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	
}
