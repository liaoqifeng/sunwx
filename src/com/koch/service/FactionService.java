package com.koch.service;

import java.util.List;
import java.util.Map;

import com.koch.entity.Faction;
import com.koch.entity.Post;

public interface FactionService extends BaseService<Faction> {
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

	public String getName (Integer id);
	
	
	public List<Faction> getAlls();
	
	public Map<Integer,String>  getFactionMap() ;
}
