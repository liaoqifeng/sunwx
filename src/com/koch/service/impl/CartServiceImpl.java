package com.koch.service.impl;

import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.koch.dao.CartDao;
import com.koch.dao.MemberDao;
import com.koch.entity.Cart;
import com.koch.entity.CartItem;
import com.koch.entity.Member;
import com.koch.service.CartService;
import com.koch.util.CookieUtils;
import com.koch.util.GlobalConstant;

@Service
public class CartServiceImpl extends BaseServiceImpl<Cart> implements CartService {
	@Resource
	private CartDao cartDao;
	@Resource
	private MemberDao memberDao;

	@Resource
	public void setBaseDao(CartDao cartDao) {
		super.setBaseDao(cartDao);
	}

	public Cart getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			//Member member = (Member) request.getSession().getAttribute(GlobalConstant.MEMBER_SESSION_USER);
			//暂无登录用户
			Member member = memberDao.get(1);
			if (member != null) {
				Cart cart = member.getCart();
				
				if (cart != null) {
					if (!cart.hasExpired()) {
						if (!DateUtils.isSameDay(cart.getModifyDate(),new Date())) {
							cart.setModifyDate(new Date());
							this.cartDao.update(cart);
						}
						return cart;
					}
					this.cartDao.delete(cart);
				}
			} else {
				String cartId = CookieUtils.getCookie(request, Cart.ID_COOKIE_NAME);
				String cartKey = CookieUtils.getCookie(request, Cart.KEY_COOKIE_NAME);
				if (StringUtils.isNotEmpty(cartId) && StringUtils.isNumeric(cartId) && StringUtils.isNotEmpty(cartKey)) {
					Cart cart = this.cartDao.get(Integer.valueOf(cartId));
					if (cart != null && cart.getMember() == null && StringUtils.equals(cart.getCartKey(), cartKey)) {
						if (!cart.hasExpired()) {
							if (!DateUtils.isSameDay(cart.getModifyDate(), new Date())) {
								cart.setModifyDate(new Date());
								this.cartDao.update(cart);
							}
							return cart;
						}
						this.cartDao.delete(cart);
					}
				}
			}
		}
		return null;
	}
}
