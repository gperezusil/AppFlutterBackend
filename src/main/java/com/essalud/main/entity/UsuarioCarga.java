package com.essalud.main.entity;

import java.io.Serializable;


public class UsuarioCarga implements Serializable {

	private Long id;

	private String user_name;

	private String a_paterno;

	private String a_materno;

	private String nombres;

	private String contrasena;
	
	private String correo;

	public UsuarioCarga(Long id, String user_name, String a_paterno, String a_materno, String nombres,
			String contrasena, String correo) {
		this.id = id;
		this.user_name = user_name;
		this.a_paterno = a_paterno;
		this.a_materno = a_materno;
		this.nombres = nombres;
		this.contrasena = contrasena;
		this.correo=correo;
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
	
	

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}



	private static final long serialVersionUID = 1L;

	
}
