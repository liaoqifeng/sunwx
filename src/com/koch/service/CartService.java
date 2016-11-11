package com.koch.service;

import com.koch.entity.Cart;

public interface CartService extends BaseService<Cart>{
	public Cart getCurrent();
}
