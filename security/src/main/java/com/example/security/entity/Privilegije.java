package com.example.security.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Privilegije {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String privilageName;
	
	@ManyToMany(mappedBy = "privilegije")
	private Collection<UserRole> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivilageName() {
		return privilageName;
	}

	public void setPrivilageName(String privilageName) {
		this.privilageName = privilageName;
	}

	public Collection<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<UserRole> roles) {
		this.roles = roles;
	}

	public Privilegije(Long id, String privilageName, Collection<UserRole> roles) {
		super();
		this.id = id;
		this.privilageName = privilageName;
		this.roles = roles;
	}
	
	public Privilegije() {}
}
