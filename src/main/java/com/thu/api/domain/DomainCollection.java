package com.thu.api.domain;

import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;


@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public abstract class DomainCollection<T>{
	
	private Collection<T> entities;
	
	@XmlTransient 
	public Collection<T> getEntities() {
		return entities;
	}
	
	public void setEntities(Collection<T> entities) {
		this.entities = entities;
	}
	
	@Override
	public String toString() {
		try {
			JAXBContext context = JAXBContext.newInstance(getClass());
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(this, writer);
			return writer.getBuffer().toString();
		} catch (Exception e) {
			return super.toString();
		}
		
	}

	
}
