package com.koch.service;

import java.util.List;

import com.koch.bean.WeChatMessage;
import com.koch.entity.PostJoin;

public interface PostJoinService extends BaseService<PostJoin> {
	// public Order update(Order t,Admin operator);
	// public Order build(Cart cart, Receiver receiver, Paymentway paymentway,
	// Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String
	// invoiceTitle, boolean useDeposit, String remark);
	// public Order create(Cart cart, Receiver receiver, Paymentway paymentway,
	// Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String
	// invoiceTitle, boolean useDeposit, String remark, Admin operator);
	// public void process(Order order, Admin operator);
	// public void complete(Order order, Admin operator);
	// public void invalid(Order order, Admin operator);
	// public void payment(Order order,PaymentInfo paymentInfo, Admin operator);
	// public void deliver(Order order,DeliverInfo deliverInfo,Admin operator);
	// public void refunds(Order order,Refunds refunds, Admin operator);
	// public void returns(Order order,Returns returns, Admin operator);

	public WeChatMessage doJoin(Integer memberId, Integer postId);
	
	public WeChatMessage cancelJoin(Integer memberId, Integer postId);
	
	public boolean isJoin(Integer memberId, Integer postId);

	public List<PostJoin> getJoins(Integer postId);
}
