package com.koch.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="bonus")
public class Bonus extends BaseEntity{ 
	private static final long serialVersionUID = -6222986743118933629L;
	
	private String nickName; //提供方名称
	private String sendName; //商户名称
	private String activeName; //活动名称
	private String wishing; //红包祝福语
	private String remark; //备注
	private String logo; //商户logo的url
	private Integer count; //红包发放总人数
	private BigDecimal totalAmount; //红包总金额
	private BigDecimal minAmount; //最小红包金额 
	private BigDecimal maxAmount; //最大红包金额
	private Date beginDate; //开始时间
	private Date endDate; //结束时间
	private Boolean isPublish; //是否发布
	
	private Set<BonusInfo> bonusInfos = new HashSet<BonusInfo>();
	
	@Column
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	@Column
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	@Column
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Column
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column(precision=18, scale=2)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Column(precision=18, scale=2)
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	@Column(precision=18, scale=2)
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}
	@Column(nullable = false)
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Column(nullable = false)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	@JsonIgnore
	@OneToMany(mappedBy="bonus",fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.ALL},orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<BonusInfo> getBonusInfos() {
		return bonusInfos;
	}
	public void setBonusInfos(Set<BonusInfo> bonusInfos) {
		this.bonusInfos = bonusInfos;
	}
	
	@Transient
	public BigDecimal totalSendAmount(){
		if(bonusInfos == null || bonusInfos.size() <= 0){
			return new BigDecimal(0);
		}
		BigDecimal b = new BigDecimal(0);
		Iterator<BonusInfo> itor = getBonusInfos().iterator();
		while(itor.hasNext()){
			b.add(itor.next().getAmount());
		}
		return b;
	}
	
	@Transient
	public Integer totalSendCount(){
		if(bonusInfos == null || bonusInfos.size() <= 0){
			return 0;
		}
		return bonusInfos.size();
	}
}
