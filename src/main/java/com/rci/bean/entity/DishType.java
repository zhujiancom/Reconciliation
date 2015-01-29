package com.rci.bean.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rci.annotation.ColumnName;

/**
 * 菜品类型表， 与收银机系统表 [ cybr_bt_dish_type ]同步。如：火锅，香辣炸鸡，饮料
 * @author zj
 *
 */
@Entity
@Table(name="tb_dish_type")
public class DishType extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389210770108478872L;

	private Integer version;
	
	private Long dtid;
	
	/* 类型编号  */
	private String dtNo;
	
	/* 类型名称  */
	private String dtName;
	
	private List<Dish> dishes;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="dtid", nullable=false,updatable=false)
	public Long getDtid() {
		return dtid;
	}

	public void setDtid(Long dtid) {
		this.dtid = dtid;
	}

	@Column(name="dish_type_no")
	public String getDtNo() {
		return dtNo;
	}

	@ColumnName("ch_typeno")
	public void setDtNo(String dtNo) {
		this.dtNo = dtNo;
	}

	@Column(name="dish_type_name")
	public String getDtName() {
		return dtName;
	}

	@ColumnName("vch_typename")
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="dishType")
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
