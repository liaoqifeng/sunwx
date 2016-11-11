package com.koch.service;

import org.springframework.transaction.annotation.Transactional;

import com.koch.bean.Pager;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostBadge;
import com.koch.entity.PostNotice;

public interface PostService extends BaseService<Post> {
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

	public Pager<Post> findPosts(Integer postSectorId, Integer factionId, Integer memberId, Integer postType,
			Integer minRow, Member currentMember);

	public Post deletePost(Integer postId);

	public Integer addPost(Post post, String pics, String path);

	public Long getBadgeCount(Integer memberId);

	public void updateBadeg(Integer memberId);

	public Pager<Post> findFirstPosts(Integer postSectorId, Integer factionId, Integer memberId, Integer postType,
			Integer minRow, Member currentMember);

	public void saveBadge(Member currentMember);

	public void updateBadegByPost(Integer postId);

}
