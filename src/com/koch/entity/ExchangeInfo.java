package com.koch.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@Table(name="exchange_info")
@JsonFilter("exchangeInfo")
public class ExchangeInfo extends BaseEntity{
	private static final long serialVersionUID = 6248940018892027741L;
	
	private Member member;
	private Receiver receiver;
	private Product product;
	private String productName;
	private String productImage;
	private Integer score;
	private BigDecimal salePrice;
	private Boolean isCollect;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receiverId")
	public Receiver getReceiver() {
		return receiver;
	}
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	@Column
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Column
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	@Column
	public Boolean getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(Boolean isCollect) {
		this.isCollect = isCollect;
	}
	@PrePersist
	public void prePersist() {
		if (getProduct() != null){
			setProductName(getProduct().getFullName());
			setSalePrice(getProduct().getSalePrice());
			setProductImage(getProduct().getShowImg());
			setScore(getProduct().getCoinPrice().intValue());
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (getProduct() != null){
			setProductName(getProduct().getFullName());
			setSalePrice(getProduct().getSalePrice());
			setProductImage(getProduct().getShowImg());
			setScore(getProduct().getCoinPrice().intValue());
		}
	}
	
}
