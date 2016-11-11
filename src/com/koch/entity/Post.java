package com.koch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koch.util.DateUtil;
import com.koch.util.PostConstant;
import com.koch.util.PropertiesUtil;

@Indexed
@Entity
@Table(name = "post")
@JsonFilter("post")
public class Post extends BaseEntity {
	private static final long serialVersionUID = 9041677477874162977L;

	private String title;
	private int type;
	private String content;
	private Member member;
	private PostSector postSector;
	private Date beginDate;
	private Date endDate;

	private String area;

	private String postSectorName;

	private Long postPraiseCount;

	private Long postCommentCount;
	
	private Long badge = 0l;

	private PostConstant.PostStatus status;

	private List<PostImage> postImages = new ArrayList<PostImage>();

	private List<PostComment> postComments = new ArrayList<PostComment>();

	private List<PostPraise> postPraises = new ArrayList<PostPraise>();

	private List<PostJoin> postJoins = new ArrayList<PostJoin>();

	private boolean praised;

	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column
	public PostConstant.PostStatus getStatus() {
		return status;
	}

	public void setStatus(PostConstant.PostStatus status) {
		this.status = status;
	}

	@ManyToOne()
	@JoinColumn(name = "memberId", nullable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@OrderBy("index asc")
	public List<PostImage> getPostImages() {
		return postImages;
	}

	public void setPostImages(List<PostImage> postImages) {
		this.postImages = postImages;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@OrderBy("createDate desc")
	public List<PostComment> getPostComments() {
		return postComments;
	}

	public void setPostComments(List<PostComment> postComments) {
		this.postComments = postComments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@OrderBy("createDate desc")
	public List<PostPraise> getPostPraises() {
		return postPraises;
	}

	public void setPostPraises(List<PostPraise> postPraises) {
		this.postPraises = postPraises;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sectorId")
	public PostSector getPostSector() {
		return postSector;
	}

	public void setPostSector(PostSector postSector) {
		this.postSector = postSector;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@OrderBy("createDate desc")
	public List<PostJoin> getPostJoins() {
		return postJoins;
	}

	public void setPostJoins(List<PostJoin> postJoins) {
		this.postJoins = postJoins;
	}

	@Transient
	public String getShowDate() {
		return DateUtil.getShowDate(getCreateDate());
	}

	@Transient
	public String getPostSectorName() {
		return postSectorName;
	}

	public void setPostSectorName(String postSectorName) {
		this.postSectorName = postSectorName;
	}

	@Transient
	public Long getPostPraiseCount() {
		return postPraiseCount;
	}

	public void setPostPraiseCount(Long postPraiseCount) {
		this.postPraiseCount = postPraiseCount;
	}

	@Transient
	public Long getPostCommentCount() {
		return postCommentCount;
	}

	public void setPostCommentCount(Long postCommentCount) {
		this.postCommentCount = postCommentCount;
	}

	@Transient
	public String getBeginDateStr() {
		return beginDate != null ? DateUtil.parseDate(beginDate, "MM月dd日  HH:mm") : null;
	}

	@Transient
	public String getEndDateStr() {
		return endDate != null ? DateUtil.parseDate(endDate, "MM月dd日  HH:mm") : null;
	}

	@Transient
	public boolean isPraised() {
		return praised;
	}

	public void setPraised(boolean praised) {
		this.praised = praised;
	}
	
	
	
	@Transient
	public Long getBadge() {
		return badge;
	}

	public void setBadge(Long badge) {
		this.badge = badge;
	}

	@Transient
	public String getPostImagesStr() {
		String s = "";
		if (postImages != null) {
			for (int i = 0; i < postImages.size(); i++) {
				PostImage pi = postImages.get(i);
				s += ","  + pi.getName();
			}
			if (s.length() > 0) {
				s = s.substring(1);
			}
		}
		return s;
	}

}
