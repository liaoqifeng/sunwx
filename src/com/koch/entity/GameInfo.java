package com.koch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="game_info")
public class GameInfo extends BaseEntity{ 
	private static final long serialVersionUID = -2798693087075005180L;
	private Game game;
	private Member member;
	private Integer hortationIndex;
	private String hortationTitle;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gameId")
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Column
	public Integer getHortationIndex() {
		return hortationIndex;
	}
	public void setHortationIndex(Integer hortationIndex) {
		this.hortationIndex = hortationIndex;
	}
	@Column
	public String getHortationTitle() {
		return hortationTitle;
	}
	public void setHortationTitle(String hortationTitle) {
		this.hortationTitle = hortationTitle;
	}
	
}
