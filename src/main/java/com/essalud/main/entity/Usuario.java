package com.essalud.main.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class Usuario implements Serializable {

	@Id
	@Column(name = "IDUSER")
	private Long id;
	@Column(name = "USERNAME")
	private String user_name;
	@Column(name = "APPATERNO")
	private String a_paterno;
	@Column(name = "APMATERNO")
	private String a_materno;
	@Column(name = "NOMBRES")
	private String nombres;
	@Column(name = "PASSWD")
	private String contrasena;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDUSER",referencedColumnName = "IDUSER")
	private Usuario_Datos usuario_datos;
	
	
	public Usuario_Datos getUsuario_datos() {
		return usuario_datos;
	}






	public void setUsuario_datos(Usuario_Datos usuario_datos) {
		this.usuario_datos = usuario_datos;
	}






	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public String getUser_name() {
		return user_name;
	}






	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}






	public String getA_paterno() {
		return a_paterno;
	}






	public void setA_paterno(String a_paterno) {
		this.a_paterno = a_paterno;
	}






	public String getA_materno() {
		return a_materno;
	}






	public void setA_materno(String a_materno) {
		this.a_materno = a_materno;
	}






	public String getNombres() {
		return nombres;
	}






	public void setNombres(String nombres) {
		this.nombres = nombres;
	}






	public String getContrasena() {
		return contrasena;
	}






	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}






	private static final long serialVersionUID = 1L;

}
