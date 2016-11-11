package com.koch.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.koch.entity.Post;
import com.koch.service.PostService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring3-servlet.xml", "classpath*:/spring3-cache.xml"})
@Transactional
public class PostServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImplTest.class);
	
//	@PersistenceContext
//	protected EntityManager entityManager;
//	@Resource
//	private ArticleDao articleDao;
//	@Resource
//	private ProductDao productDao;
	@Resource
	private PostService postService;

	@Test
	public void indexTest(){
		Post p = this.postService.get(1);
		System.out.println(p.getTitle());
		System.out.println(p.getPostImages().get(0).getPath());
	}
}
