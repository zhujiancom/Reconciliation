package com.rci.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rci.annotation.ColumnName;

/**
 * 菜品表 ，香辣炸鸡（小），部队火锅等。 饮料
 * 
 * 定期与收银机系统表[ cybr_bt_dish ]同步
 * @author zj
 *
 */
@Entity
@Table(name="tb_dish")
public class Dish extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600540671939607021L;

	private Integer version;
	
	private Long did;
	
	/* 产品编号  */
	private String dishNo;
	
	/* 产品名称   */
	private String dishName;
	
	/* 产品价格  */
	private BigDecimal dishPrice;
	
	/* 产品添加时间   */
	private Date createTime;
	
	/* 产品类型  */
	private DishType dishType;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="did", nullable=false,updatable=false)
	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	@Column(name="dish_no")
	public String getDishNo() {
		return dishNo;
	}

	@ColumnName("ch_dishno")
	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}
	
	@Column(name="dish_name")
	public String getDishName() {
		return dishName;
	}

	@ColumnName("vch_dishname")
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	@Column(name="dish_price")
	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	@ColumnName("num_price1")
	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	@ColumnName("dt_build")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="dish_type_id")
	public DishType getDishType() {
		return dishType;
	}

	@ColumnName("ch_typeno")
	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

//	@Override
//	public Serializable getId() {
//		return did;
//	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion(Integer version) {
		this.version = version;
	}
}
