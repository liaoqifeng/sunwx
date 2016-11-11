package com.koch.controller.shop;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koch.base.BaseController;
import com.koch.bean.OrderBy;
import com.koch.entity.Product;
import com.koch.entity.Property;
import com.koch.service.ProductService;
import com.koch.service.PropertyService;
/**
 * 商品详情页面控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller("shopProductController")
@RequestMapping(value="product")
public class ProductController extends BaseController{
	@Resource
	private ProductService productService;
	@Resource
	private PropertyService propertyService;
	
	@RequestMapping(value="content/{id}")
	public String list(@PathVariable Integer id,ModelMap modelMap){
		Product product = productService.get(id);
		modelMap.addAttribute("product", product);
		List<Property> propertys = propertyService.getList("productCategory", product.getProductCategory(),new OrderBy("orderList", OrderBy.OrderType.asc));
		modelMap.addAttribute("propertys", propertys);
		
		return "/shop/product/content";
    }
	
}
