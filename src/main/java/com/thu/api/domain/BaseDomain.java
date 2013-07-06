package com.thu.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseDomain extends AbstractPersistable<Long> {
	
	private static final long serialVersionUID = -7279992430910354837L;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date creationDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate = new Date();
	
	private Boolean active = Boolean.TRUE;
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date date) {
		this.updateDate = new Date();
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? super.hashCode() : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDomain other = (BaseDomain) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
			else 
				return super.equals(obj);
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}
