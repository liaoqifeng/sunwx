package com.koch.controller.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koch.base.BaseController;
import com.koch.bean.JsonMessage;
import com.koch.entity.Cart;
import com.koch.entity.CartItem;
import com.koch.entity.Member;
import com.koch.entity.Product;
import com.koch.service.CartItemService;
import com.koch.service.CartService;
import com.koch.service.MemberService;
import com.koch.service.ProductService;
import com.koch.util.CookieUtils;
import com.koch.util.JsonUtil;
import com.koch.util.SpringUtil;
/**
 * 购物车控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller("shopCartController")
@RequestMapping(value="cart")
public class CartController extends BaseController{
	@Resource
	private ProductService productService;
	@Resource
	private CartService cartService;
	@Resource
	private CartItemService cartItemService;
	@Resource
	private MemberService memberService;

	@RequestMapping(value = { "/add" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonMessage add(Integer id, Integer quantity,
			HttpServletRequest request, HttpServletResponse response) {
		if ((quantity == null) || (quantity.intValue() < 1)) {
			return save_error;
		}
		Product product = this.productService.get(id);
		if (product == null) {
			return JsonMessage.warn("shop.cart.productNotExsit", new Object[0]);
		}
		if (!product.getIsPublish().booleanValue()) {
			return JsonMessage.warn("shop.cart.productNotMarketable",
					new Object[0]);
		}
		if (product.getIsGift().booleanValue()) {
			return JsonMessage.warn("shop.cart.notForSale", new Object[0]);
		}
		Cart cart = this.cartService.getCurrent();
		Member member = this.memberService.get(1);
		if (cart == null) {
			cart = new Cart();
			cart.setCartKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
			cart.setMember(member);
			cart.setIsReload(true);
			this.cartService.save(cart);
		}
		if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT.intValue()) {
			return JsonMessage.warn("shop.cart.addCountNotAllowed", new Object[] { Cart.MAX_PRODUCT_COUNT });
		}
		if (cart.contains(product)) {
			CartItem cartItem = cart.getCartItem(product);
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity().intValue() + quantity.intValue() > CartItem.MAX_QUANTITY.intValue()) {
				return JsonMessage.warn("shop.cart.maxCartItemQuantity", new Object[] { CartItem.MAX_QUANTITY });
			}
			if (product.getStock() != null && cartItem.getQuantity().intValue() + quantity.intValue() > product.getAvailableStock().intValue()) {
				return JsonMessage.warn("shop.cart.productLowStock", new Object[0]);
			}
			cartItem.add(quantity.intValue());
			this.cartItemService.update(cartItem);
		} else {
			if (CartItem.MAX_QUANTITY != null && quantity.intValue() > CartItem.MAX_QUANTITY.intValue()) {
				return JsonMessage.warn("shop.cart.maxCartItemQuantity", new Object[] { CartItem.MAX_QUANTITY });
			}
			if (product.getStock() != null && quantity.intValue() > product.getAvailableStock().intValue()) {
				return JsonMessage.warn("shop.cart.productLowStock", new Object[0]);
			}
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			this.cartItemService.save(cartItem);
			cart.getCartItems().add(cartItem);
			cart.setIsReload(true);
		}
		if (member == null) {
			CookieUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
			CookieUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getCartKey(), Cart.TIMEOUT);
		}
		return JsonMessage.success("shop.cart.addSuccess", new Object[] {
				Integer.valueOf(cart.getQuantity()),
				currency(cart.getDiscountAfterAmount(), true, false) });
	}
	
	@RequestMapping(value = { "/edit" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> edit(Integer id, Integer quantity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (quantity == null || quantity.intValue() < 1) {
			map.put("message", update_error);
			return map;
		}
		Cart cart = this.cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			map.put("message", JsonMessage.error("shop.cart.notEmpty", new Object[0]));
			return map;
		}
		CartItem cartItem = this.cartItemService.get(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItems == null || cartItems == null || !cartItems.contains(cartItem)) {
			map.put("message", JsonMessage.error("shop.cart.cartItemNotExsit", new Object[0]));
			return map;
		}
		if (CartItem.MAX_QUANTITY != null && quantity.intValue() > CartItem.MAX_QUANTITY.intValue()) {
			map.put("message", JsonMessage.warn("shop.cart.maxCartItemQuantity", new Object[] { CartItem.MAX_QUANTITY }));
			return map;
		}
		Product product = cartItem.getProduct();
		if (product.getStock() != null && quantity.intValue() > product.getAvailableStock().intValue()) {
			map.put("message", JsonMessage.warn("shop.cart.productLowStock", new Object[0]));
			return map;
		}
		cartItem.setQuantity(quantity);
		this.cartItemService.update(cartItem);
		map.put("message", update_success);
		map.put("subtotal", cartItem.getSubtotal());
		map.put("isLowStock", Boolean.valueOf(cartItem.getIsLowStock()));
		map.put("quantity", Integer.valueOf(cart.getQuantity()));
		map.put("score", Integer.valueOf(cart.getScore()));
		map.put("amount", cart.getDiscountAfterAmount());
		map.put("promotions", cart.getPromotions());
		map.put("giftItems", cart.getGiftItems());
		return map;
	}
	
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Cart cart = this.cartService.getCurrent();
		if ((cart == null) || (cart.isEmpty())) {
			map.put("message", JsonMessage.error("shop.cart.notEmpty", new Object[0]));
			return map;
		}
		CartItem cartItem = this.cartItemService.get(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			map.put("message", JsonMessage.error("shop.cart.cartItemNotExsit", new Object[0]));
			return map;
		}
		cartItems.remove(cartItem);
		this.cartItemService.delete(cartItem);
		map.put("message", delete_success);
		map.put("quantity", Integer.valueOf(cart.getQuantity()));
		map.put("score", Integer.valueOf(cart.getScore()));
		map.put("amount", cart.getDiscountAfterAmount());
		map.put("promotions", cart.getPromotions());
		map.put("isLowStock", Boolean.valueOf(cart.getIsLowStock()));
		return map;
	}

	@RequestMapping(value = { "/get" }, method = { RequestMethod.GET })
	@ResponseBody
	public String get() {
		String result = "";
		Cart cart = cartService.getCurrent();
		if(cart == null)
			return "null";
		if(!cart.getIsReload()){
			return "false";
		}
		Map<String, String[]> filterMap = new HashMap<String, String[]>();
		filterMap.put("cart", new String[]{"cartKey","cartItems","discountAfterAmount","quantity"});
		filterMap.put("cartItem", new String[]{"product","quantity"});
		filterMap.put("product", new String[]{"fullName","showImg","salePrice"});
		
		if(cart != null){
			result = JsonUtil.toJsonIncludeProperties(cart, filterMap);
		}
		
		return result;
	}
	
	@RequestMapping(value = { "/list" }, method = { RequestMethod.GET })
	public String list(ModelMap modelMap) {
		modelMap.addAttribute("cart", this.cartService.getCurrent());
		return "/shop/cart/cart";
	}
}
