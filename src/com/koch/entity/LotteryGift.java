package com.koch.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.koch.bean.Auth;


public class LotteryGift{ 
	private Integer index;
    private String number;
    private String name;
    private Integer count;
    private Double probability;//概率
    private String image;
    
	public LotteryGift(Integer index, String number, String name,
			Integer count, Double probability) {
		super();
		this.index = index;
		this.number = number;
		this.name = name;
		this.count = count;
		this.probability = probability;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getProbability() {
		return probability;
	}
	public void setProbability(Double probability) {
		this.probability = probability;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
    public String toString() {
        return "Gift [index=" + index + ", number=" + number + ", name=" + name + ", probability=" + probability + "]";
    }
}
