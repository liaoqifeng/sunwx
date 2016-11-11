package com.koch.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="bonus_info")
public class BonusInfo extends BaseEntity{ 
	private static final long serialVersionUID = -4833834135373505117L;
	
	private Member member; 
	private Bonus bonus; 
	private String mchBillno; //商户订单号
	private String sendListid; //微信单号
	private String sendTime; //发放成功时间
	private BigDecimal amount; //付款金额
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bonusId")
	public Bonus getBonus() {
		return bonus;
	}
	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
	}
	@Column
	public String getMchBillno() {
		return mchBillno;
	}
	public void setMchBillno(String mchBillno) {
		this.mchBillno = mchBillno;
	}
	@Column
	public String getSendListid() {
		return sendListid;
	}
	public void setSendListid(String sendListid) {
		this.sendListid = sendListid;
	}
	@Column
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(precision=18, scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
