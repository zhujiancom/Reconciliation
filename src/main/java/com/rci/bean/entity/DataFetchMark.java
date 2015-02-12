package com.rci.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="tb_datafetch_mark")
public class DataFetchMark extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2288370372100976215L;
	private Long markId;
	private Integer version;
	
	/* 对账日期  */
	private String rciDate;
	
	/* 标记 */
	private Integer mark;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="mark_id", nullable=false,updatable=false)
	public Long getMarkId() {
		return markId;
	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	@Column(name="rci_date")
	public String getRciDate() {
		return rciDate;
	}

	public void setRciDate(String rciDate) {
		this.rciDate = rciDate;
	}

	@Column(name="mark_flag")
	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	@Override
	public Integer getVersion() {
		// TODO Auto-generated method stub
		return version;
	}

	@Override
	@Version
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Boolean isMarked(){
		if(mark==null || mark==0){
			return false;
		}
		return true;
	}

}
