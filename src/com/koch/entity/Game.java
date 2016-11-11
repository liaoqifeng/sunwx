package com.koch.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koch.bean.WeChatMessage;


@Entity
@Table(name="game")
public class Game extends BaseEntity{

	private static final long serialVersionUID = 6522847420135346617L;
	public enum GameType{
		rotate, guaguale, sudoku, slot,other;
	}
	public enum GameCycle{
		everyDay,everyWeek,everyMonth,everyYear;
	}
	public enum GameStatus{
		progress, maintain, close;
	}
	
	private String title;
	private GameType type;
	private GameCycle cycle;
	private GameStatus status;
	private Integer count;
	private String rules;
	private String image;
	private Integer beginTime;
	private Integer endTime;
	private String description;
	private String expression;
	
	private List<GameItem> gameItems = new ArrayList<GameItem>();
	
	private Boolean isComplete;
	private Integer score;
	
	@Column
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Enumerated
	@Column
	public GameType getType() {
		return type;
	}
	public void setType(GameType type) {
		this.type = type;
	}
	@Enumerated
	@Column
	public GameCycle getCycle() {
		return cycle;
	}
	public void setCycle(GameCycle cycle) {
		this.cycle = cycle;
	}
	@Enumerated
	@Column
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	@Column
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Column
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	@Column
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column
	public Integer getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
	}
	@Column
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonIgnore
	@OneToMany(mappedBy="game",fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.ALL},orphanRemoval=true)
	@OrderBy("hortationIndex asc")
	public List<GameItem> getGameItems() {
		return gameItems;
	}
	public void setGameItems(List<GameItem> gameItems) {
		this.gameItems = gameItems;
	}
	@Column
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Transient
	public Boolean getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	@Transient
	public Integer getScore(Integer size) {
		if(StringUtils.isNotEmpty(getExpression())){
			List<Variable> variables = new ArrayList<Variable>();
			String expression = getExpression();
			if (expression.indexOf("count") > -1) {
				variables.add(Variable.createVariable("count", size));
			}
			Object result = ExpressionEvaluator.evaluate(expression, variables);
			return Integer.valueOf(result.toString());
		}
		return 0;
	}
	
}
