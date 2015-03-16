package com.rci.bean.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 支付方式
 * @author zj
 *
 */
@Entity
@Table(name="tb_paymode")
public class Paymode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4818290752801757653L;

	private Integer version;
	
	private Long pmid;
	
	private String modeNo;
	
	private String modeName;
	
	private String remark;
	
	private List<Scheme> schemes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // MYSQL ID generator
	@Column(name="pmid", nullable=false,updatable=false)
	public Long getPmid() {
		return pmid;
	}

	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}

	@Column(name="paymode_no")
	public String getModeNo() {
		return modeNo;
	}

	public void setModeNo(String modeNo) {
		this.modeNo = modeNo;
	}

	@Column(name="paymode_name")
	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="paymode_id")
	public List<Scheme> getSchemes() {
		return schemes;
	}

	public void setSchemes(List<Scheme> schemes) {
		this.schemes = schemes;
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
