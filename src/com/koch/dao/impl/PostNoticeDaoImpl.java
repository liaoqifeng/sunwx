package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.PostNoticeDao;
import com.koch.entity.PostNotice;

@Repository("postNoticeDao")
public class PostNoticeDaoImpl extends BaseDaoImpl<PostNotice> implements PostNoticeDao {

}
