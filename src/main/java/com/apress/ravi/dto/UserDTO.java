package com.apress.ravi.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Users")
public class UserDTO {
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;

	@NotEmpty(message = "error.name.empty")
	@Length(max = 15, message = "error.name.length")
	@Column(name = "NAME")
	private String name;
	
	@NotEmpty(message = "error.address.empty")
	@Length(min = 10, message = "error.address.length")
	@Column(name = "ADDRESS")
	private String address;
	
	@Email(message = "error.email.email")
	@NotEmpty(message = "error.email.empty")
	@Length(min = 10, message = "error.email.length")
	@Column(name = "EMAIL")
	private String email;
	// Getters and Setters methods
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}