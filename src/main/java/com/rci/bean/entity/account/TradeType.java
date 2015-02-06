package com.rci.bean.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rci.bean.entity.BaseEntity;

/**
 * 交易类型表， 如：收入，支出，转账
 * @author zj
 *
 */
@Entity
@Table(name="tb_acc_trade_type")
public class TradeType extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8826746435889221938L;

	private Integer version;
	
	private Long ttid;
	
	/* 类型名称 */
	private String typeName;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="ttid", nullable=false,updatable=false)
	public Long getTtid() {
		return ttid;
	}

	public void setTtid(Long ttid) {
		this.ttid = ttid;
	}

	@Column(name="typename")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

//	@Override
//	public Serializable getId() {
//		return ttid;
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
