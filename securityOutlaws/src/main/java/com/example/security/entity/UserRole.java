package com.example.security.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<User> userCollection;
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "privilegije_odredjenih_rola",
    joinColumns = { @JoinColumn(name = "id_R")},
    inverseJoinColumns = {@JoinColumn(name = "id_P")})
	private Collection<Privilegije> privilegije;
	
	@Column
	private String roleName;

	public UserRole() {}
	
	public UserRole(Long id, Collection<User> userCollection, Collection<Privilegije> privilegije, String roleName) {
		super();
		this.id = id;
		this.userCollection = userCollection;
		this.privilegije = privilegije;
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<User> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Collection<User> userCollection) {
		this.userCollection = userCollection;
	}

	public Collection<Privilegije> getPrivilegije() {
		return privilegije;
	}

	public void setPrivilegije(Collection<Privilegije> privilegije) {
		this.privilegije = privilegije;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
