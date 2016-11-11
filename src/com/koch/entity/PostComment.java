package com.koch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.koch.util.DateUtil;
import com.koch.util.PostConstant;

@Indexed
@Entity
@Table(name = "post_comment")
@JsonFilter("post_comment")
public class PostComment extends BaseEntity {
	private static final long serialVersionUID = 9041677477874162977L;

	private String content;
	private Member member;

	private Member replyMember;

	private PostComment replyComment;

	private int postId;

	private PostConstant.PostStatus status;

	private boolean isBelong = false;

	private boolean praised = false;

	private Integer badge = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replyMemberId", nullable = false)
	public Member getReplyMember() {
		return replyMember;
	}

	public void setReplyMember(Member replyMember) {
		this.replyMember = replyMember;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replyCommentId", nullable = false)
	public PostComment getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(PostComment replyComment) {
		this.replyComment = replyComment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId", nullable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Column
	public PostConstant.PostStatus getStatus() {
		return status;
	}

	public void setStatus(PostConstant.PostStatus status) {
		this.status = status;
	}

	@Transient
	public String getShowDate() {
		return DateUtil.getShowDate(getCreateDate());
	}

	@Transient
	public boolean isBelong() {
		return isBelong;
	}

	public void setBelong(boolean isBelong) {
		this.isBelong = isBelong;
	}

	@Transient
	public boolean isPraised() {
		return praised;
	}

	public void setPraised(boolean praised) {
		this.praised = praised;
	}

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
	}

}
