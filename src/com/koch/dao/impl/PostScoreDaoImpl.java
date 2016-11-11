package com.koch.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.koch.dao.PostScoreDao;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostScore;
import com.koch.util.DateUtil;

@Repository("postScoreDao")
public class PostScoreDaoImpl extends BaseDaoImpl<PostScore> implements PostScoreDao {
	@Override
	public PostScore getPostScore(Member member) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date d = DateUtil.getCurrentDateStr();
		map.put("member", member);
		map.put("date", d);

		PostScore postScore = get(map);
		if (postScore == null) {
			postScore = new PostScore();
			postScore.setDate(d);
			postScore.setMember(member);
			postScore.setScore(0);
		}
		return postScore;
	}

}
