package com.koch.service;

import java.util.List;
import java.util.Map;

import com.koch.bean.WeChatMessage;
import com.koch.entity.Member;
import com.koch.entity.PostCommentPraise;
import com.koch.entity.PostPraise;

public interface PostCommentPraiseService extends BaseService<PostCommentPraise>{
	
	public WeChatMessage doPraise(Member member,Integer postId);
	
//	public Order update(Order t,Admin operator);
//	public Order build(Cart cart, Receiver receiver, Paymentway paymentway, Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String invoiceTitle, boolean useDeposit, String remark);
//	public Order create(Cart cart, Receiver receiver, Paymentway paymentway, Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String invoiceTitle, boolean useDeposit, String remark, Admin operator);
//	public void process(Order order, Admin operator);
//	public void complete(Order order, Admin operator);
//	public void invalid(Order order, Admin operator);
//	public void payment(Order order,PaymentInfo paymentInfo, Admin operator);
//	public void deliver(Order order,DeliverInfo deliverInfo,Admin operator);
//	public void refunds(Order order,Refunds refunds, Admin operator);
//	public void returns(Order order,Returns returns, Admin operator);
	
	
//	public List<PostPraise> getPraises(Integer postId);
	
//	public Map<Integer,Integer> getPraiseCount(List<Integer> postIds);
	
//	public WeChatMessage cancelPraise(Integer memberId,Integer postId);
//	
//	public boolean isPraise(Integer memberId,Integer postId);
	

}
