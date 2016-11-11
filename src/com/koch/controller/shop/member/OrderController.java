package com.koch.controller.shop.member;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koch.base.BaseController;
import com.koch.bean.JsonMessage;
import com.koch.entity.Cart;
import com.koch.entity.Coupon;
import com.koch.entity.CouponInfo;
import com.koch.entity.Deliverway;
import com.koch.entity.Member;
import com.koch.entity.Order;
import com.koch.entity.Paymentway;
import com.koch.entity.Receiver;
import com.koch.service.AreaService;
import com.koch.service.CartService;
import com.koch.service.CouponInfoService;
import com.koch.service.DeliverwayService;
import com.koch.service.MemberService;
import com.koch.service.OrderService;
import com.koch.service.PaymentwayService;
import com.koch.service.ProductService;
import com.koch.service.ReceiverService;
/**
 * 订单处理控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller("shopOrderController")
@RequestMapping(value="member/order")
public class OrderController extends BaseController{
	@Resource
	private CartService cartService;
	@Resource
	private OrderService orderService;
	@Resource
	private DeliverwayService deliverwayService;
	@Resource
	private PaymentwayService paymentwayService;
	@Resource
	private ReceiverService receiverService;
	@Resource
	private CouponInfoService couponInfoService;
	@Resource
	private AreaService areaService;
	@Resource
	private MemberService memberService;

	@RequestMapping(value = { "/settle" }, method = { RequestMethod.GET })
	public String settle(ModelMap model) {
		Cart cart = this.cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			return "redirect:/cart/list.shtml";
		}

		Order order = this.orderService.build(cart, null, null, null, null, false, null, false, null);
		model.addAttribute("order", order);
		model.addAttribute("cartToken", cart.getToken());
		model.addAttribute("paymentways", this.paymentwayService.findAll());
		model.addAttribute("deliverways", this.deliverwayService.findAll());
		return "/shop/member/order/settle";
	}
	
	@RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonMessage create(String cartToken, Integer receiverId,
			Integer paymentwayId, Integer deliverwayId, String code,
			@RequestParam(defaultValue = "false") Boolean isInvoice,
			String invoiceTitle,
			@RequestParam(defaultValue = "false") Boolean useBalance,
			String remark) {
		Cart cart = this.cartService.getCurrent();
		if ((cart == null) || (cart.isEmpty())) {
			return JsonMessage.warn("shop.order.cartNotEmpty");
		}
		if (!StringUtils.equals(cart.getToken(), cartToken)) {
			return JsonMessage.warn("shop.order.cartHasChanged");
		}
		if (cart.getIsLowStock()) {
			return JsonMessage.warn("shop.order.cartLowStock");
		}
		Receiver receiver = this.receiverService.get(receiverId);
		if (receiver == null) {
			return JsonMessage.error("shop.order.receiverNotExsit");
		}
		Paymentway paymentway = this.paymentwayService.get(paymentwayId);
		if (paymentway == null) {
			return JsonMessage.error("shop.order.paymentMethodNotExsit");
		}
		Deliverway deliverway = this.deliverwayService.get(deliverwayId);
		if (deliverway == null) {
			return JsonMessage.error("shop.order.shippingMethodNotExsit");
		}
		if (!paymentway.getDeliverways().contains(deliverway)) {
			return JsonMessage.error("shop.order.deliveryUnsupported");
		}
		CouponInfo couponInfo = this.couponInfoService.findByCode(code);
		Order order = this.orderService.create(cart, receiver, paymentway,
				deliverway, couponInfo, isInvoice.booleanValue(), invoiceTitle,
				useBalance.booleanValue(), remark, null);
		return JsonMessage.success(order.getNumber(), new Object[0]);
	}
	
	@RequestMapping(value = { "/coupon_used" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> couponInfo(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cart cart = this.cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			map.put("message", JsonMessage.warn("shop.order.cartNotEmpty", new Object[0]));
			return map;
		}
		if (!cart.isCouponAllowed()) {
			map.put("message", JsonMessage.warn("shop.order.couponNotAllowed", new Object[0]));
			return map;
		}
		CouponInfo couponInfo = this.couponInfoService.findByCode(code);
		if (couponInfo != null && couponInfo.getCoupon() != null) {
			Coupon coupon = couponInfo.getCoupon();
			if (!coupon.getIsEnabled().booleanValue()) {
				map.put("message", JsonMessage.warn("shop.order.couponDisabled", new Object[0]));
				return map;
			}
			if (!coupon.hasBegun()) {
				map.put("message", JsonMessage.warn("shop.order.couponNotBegin", new Object[0]));
				return map;
			}
			if (coupon.hasExpired()) {
				map.put("message", JsonMessage.warn("shop.order.couponHasExpired", new Object[0]));
				return map;
			}
			if (!cart.isValidCoupon(coupon)) {
				map.put("message", JsonMessage.warn("shop.order.couponInvalid", new Object[0]));
				return map;
			}
			if (couponInfo.getIsUsed().booleanValue()) {
				map.put("message", JsonMessage.warn("shop.order.couponCodeUsed", new Object[0]));
				return map;
			}
			map.put("message", update_success);
			map.put("couponName", coupon.getName());
			return map;
		}
		map.put("message", JsonMessage.warn("shop.order.couponCodeNotExist", new Object[0]));
		return map;
	}
	
	@RequestMapping(value = { "/calculate" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> calculate(Integer paymentwayId,
			Integer deliverwayId, String code,
			@RequestParam(defaultValue = "false") Boolean isInvoice,
			String invoiceTitle,
			@RequestParam(defaultValue = "false") Boolean useBalance,
			String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cart cart = this.cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			map.put("message", JsonMessage.error("shop.order.cartNotEmpty"));
			return map;
		}
		CouponInfo couponInfo = this.couponInfoService.findByCode(code);
		Paymentway paymentway = this.paymentwayService.get(paymentwayId);
		Deliverway deliverway = this.deliverwayService.get(deliverwayId);
		
		Order order = this.orderService.build(cart, null, paymentway, deliverway, couponInfo, isInvoice.booleanValue(), invoiceTitle, useBalance.booleanValue(), remark);
		map.put("message", update_success);
		map.put("quantity", order.getQuantity());
		map.put("totalAmount", order.getProductAmount());
		map.put("couponAmount", order.getVoucherAmount());
		map.put("depositAmount", order.getDepositAmount());
		map.put("freight", order.getDeliveryAmount());
		map.put("tax", order.getServiceAmount());
		map.put("amountPayable", order.getAmountPayable());
		return map;
	}
	
	@RequestMapping(value = { "/save_receiver" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> saveReceiver(Receiver receiver, Integer areaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		receiver.setArea(this.areaService.get(areaId));
		receiver.setIsDefault(receiver.getIsDefault() != null);
		Member member = this.memberService.getCurrent();
		if (Receiver.MAX_RECEIVER_COUNT != null && member.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT.intValue()) {
			map.put("message", JsonMessage.error("shop.order.addReceiverCountNotAllowed", new Object[] { Receiver.MAX_RECEIVER_COUNT }));
			return map;
		}
		receiver.setMember(member);
		this.receiverService.save(receiver);
		map.put("message", save_success);
		map.put("receiver", receiver);
		return map;
	}
	
	@RequestMapping(value = { "/balance" })
	@ResponseBody
	public Double balance() {
		Member member = this.memberService.getCurrent();
		return this.memberService.getDeposit(member.getId()).doubleValue();
	}
	
	
}
