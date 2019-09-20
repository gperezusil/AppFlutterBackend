package com.essalud.main.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "USERS_DATA")
public class Usuario_Datos implements Serializable {
	@Id
	@Column(name="IDUSER")
	private Long id;
	
	@Column(name="UD_EMAIL")
	private String correo;
		
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	

	private static final long serialVersionUID = 1L;

}
