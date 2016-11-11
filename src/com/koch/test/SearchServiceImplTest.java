package com.koch.test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.ArticleDao;
import com.koch.dao.ProductDao;
import com.koch.entity.Product;
import com.koch.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring3-servlet.xml", "classpath*:/spring3-cache.xml"})
@Transactional
public class SearchServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImplTest.class);
	
	@PersistenceContext
	protected EntityManager entityManager;
	@Resource
	private ArticleDao articleDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private SearchService searchService;

	@Test
	public void indexTest(){
		this.searchService.index(Product.class);
	}
}
