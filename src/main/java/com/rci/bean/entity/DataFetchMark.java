package com.rci.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.rci.constants.enums.MarkType;

@Entity
@Table(name="tb_datafetch_mark")
public class DataFetchMark extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2288370372100976215L;
	private Long markId;
	private Integer version;
	
	private MarkType type;
	
	/* 对账日期  */
	private String rciDate;
	
	/* 标记 */
	private Integer mark;
	
	/* 增量备份点 */
	private Date savepoint;
	
	public DataFetchMark(){}
	
	public DataFetchMark(MarkType type){
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="mark_id", nullable=false,updatable=false)
	public Long getMarkId() {
		return markId;
	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	public MarkType getType() {
		return type;
	}

	public void setType(MarkType type) {
		this.type = type;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="savepoint")
	public Date getSavepoint() {
		return savepoint;
	}

	public void setSavepoint(Date savepoint) {
		this.savepoint = savepoint;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	@Override
	@Version
	public void setVersion(Integer version) {
		this.version = version;
	}
}
