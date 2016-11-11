package com.koch.service;

import java.util.List;

import com.koch.bean.WeChatMessage;
import com.koch.entity.PostJoin;
import com.koch.entity.PostNotice;

public interface PostNoticeService extends BaseService<PostNotice> {
	
	public PostNotice getPostNotice();
	
}
