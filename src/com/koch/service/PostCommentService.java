package com.koch.service;

import java.util.List;

import com.koch.bean.WeChatMessage;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostComment;
import com.koch.entity.PostSector;

public interface PostCommentService extends BaseService<PostComment> {
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

	public WeChatMessage doComment(Integer memberId, Integer postId,Integer replyCommentId, String content);
	
	public WeChatMessage doComment(Integer memberId, Integer postId, String content);
	
	public WeChatMessage cancelComment(Integer commentId);
	
	public List<PostComment> getComments(Integer postId, Member currentMember);
	
	
	public void updateBadeg(Integer memberId) ;
	

}
