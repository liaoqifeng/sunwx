package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.CartDao;
import com.koch.entity.Cart;

@Repository("cartDao")
public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao{
	
}
