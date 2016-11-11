package com.koch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.bean.Filter;
import com.koch.bean.Filter.Operator;
import com.koch.bean.OrderBy;
import com.koch.bean.OrderBy.OrderType;
import com.koch.bean.WeChatMessage;
import com.koch.dao.PostNoticeDao;
import com.koch.entity.Member;
import com.koch.entity.PostJoin;
import com.koch.entity.PostNotice;
import com.koch.service.PostNoticeService;
import com.koch.util.PostConstant;

@Service
public class PostNoticeServiceImpl extends BaseServiceImpl<PostNotice> implements PostNoticeService {
	@Resource
	private PostNoticeDao postNoticeDao;

	@Resource
	public void setBaseDao(PostNoticeDao postNoticeDao) {
		super.setBaseDao(postNoticeDao);
	}

	@Override
	public PostNotice getPostNotice() {
		List<PostNotice> list = getBaseDao().getAll(new OrderBy("index", OrderType.desc));
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
