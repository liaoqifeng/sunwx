package com.koch.service;

import com.koch.entity.Admin;
import com.koch.entity.Cart;
import com.koch.entity.CouponInfo;
import com.koch.entity.DeliverInfo;
import com.koch.entity.Deliverway;
import com.koch.entity.Order;
import com.koch.entity.PaymentInfo;
import com.koch.entity.Paymentway;
import com.koch.entity.Receiver;
import com.koch.entity.Refunds;
import com.koch.entity.Returns;

public interface OrderService extends BaseService<Order>{
	public Order update(Order t,Admin operator);
	public Order build(Cart cart, Receiver receiver, Paymentway paymentway, Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String invoiceTitle, boolean useDeposit, String remark);
	public Order create(Cart cart, Receiver receiver, Paymentway paymentway, Deliverway deliverway, CouponInfo couponInfo, boolean isInvoice, String invoiceTitle, boolean useDeposit, String remark, Admin operator);
	public void process(Order order, Admin operator);
	public void complete(Order order, Admin operator);
	public void invalid(Order order, Admin operator);
	public void payment(Order order,PaymentInfo paymentInfo, Admin operator);
	public void deliver(Order order,DeliverInfo deliverInfo,Admin operator);
	public void refunds(Order order,Refunds refunds, Admin operator);
	public void returns(Order order,Returns returns, Admin operator);
}
