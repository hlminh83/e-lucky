package com.registration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscribers")
public class Subscriber {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subscriberId; 
	private String ntid; 
	private String fullName;
	@Column(name = "group_name")
	private String group;
	
	public Subscriber(String subscriberId, String ntid, String fullName, String group) {
		super();
		this.subscriberId = subscriberId;
		this.ntid = ntid;
		this.fullName = fullName;
		this.group = group;
	}
	
	public Subscriber() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getNtid() {
		return ntid;
	}
	public void setNtid(String ntid) {
		this.ntid = ntid;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	} 
	
	
}
