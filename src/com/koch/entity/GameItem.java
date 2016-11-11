package com.koch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@Table(name="game_item")
@JsonFilter("gameItem")
public class GameItem extends BaseEntity{
	private static final long serialVersionUID = -8556570760585941761L;
	
	public enum HortationType{
		physical, virtual;
	}
	
	private Game game;
	private String title;
	private HortationType hortationType;
	private Product product;
	private Integer score;
	private Integer hortationIndex;
	private Double probability;
	private Integer count;
	private Integer winningCount;
	private String image;
	
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gameId")
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	@Enumerated
	@Column
	public HortationType getHortationType() {
		return hortationType;
	}
	public void setHortationType(HortationType hortationType) {
		this.hortationType = hortationType;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="productId")
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Column
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Column
	public Integer getHortationIndex() {
		return hortationIndex;
	}
	public void setHortationIndex(Integer hortationIndex) {
		this.hortationIndex = hortationIndex;
	}
	@Column
	public Double getProbability() {
		return probability;
	}
	public void setProbability(Double probability) {
		this.probability = probability;
	}
	@Column
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column
	public Integer getWinningCount() {
		return winningCount;
	}
	public void setWinningCount(Integer winningCount) {
		this.winningCount = winningCount;
	}
	@Column
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
