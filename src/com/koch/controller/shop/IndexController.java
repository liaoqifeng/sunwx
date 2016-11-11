package com.koch.controller.shop;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koch.base.BaseController;
import com.koch.bean.OrderBy;
import com.koch.entity.Product;
import com.koch.entity.ProductCategory;
import com.koch.entity.Property;
import com.koch.entity.Tag;
import com.koch.entity.Tag.Type;
import com.koch.service.BrandService;
import com.koch.service.ProductCategoryService;
import com.koch.service.ProductService;
import com.koch.service.PropertyService;
import com.koch.service.TagService;
/**
 * 商品详情页面控制器
 * @author koch
 * @date  2014-05-17
 */
@Controller("shopIndexController")
@RequestMapping
public class IndexController extends BaseController{
	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private BrandService brandService;
	@Resource
	private TagService tagService;
	
	private static final Integer newProductTagId = 1;
	private static final Integer hotProductTagId = 2;
	private static final Integer recommendProductTagId = 6;
	
	@RequestMapping(value="index")
	public String list(ModelMap modelMap){
		List<ProductCategory> productCategories = productCategoryService.findRoots();
		modelMap.addAttribute("productCategories", productCategories);
		modelMap.addAttribute("brands", brandService.findAll());
		Tag newTag = tagService.get(newProductTagId);
		if(newTag != null)
			modelMap.addAttribute("newProducts", newTag.getProducts());
		Tag hotTag = tagService.get(hotProductTagId);
		if(hotTag != null)
			modelMap.addAttribute("hotProducts", hotTag.getProducts());
		Tag recommendTag = tagService.get(recommendProductTagId);
		if(recommendTag != null)
			modelMap.addAttribute("recommendProducts", recommendTag.getProducts());
		return "/shop/index";
    }
	
}
