package com.essalud.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essalud.main.entity.Usuario;
import com.essalud.main.service.FirebaseService;

@RestController
@RequestMapping("/firebase/usuarios")
public class UsuarioFirebaseController {

	@Autowired
	FirebaseService firebaseService;
	
	@PostMapping("/add")
	public boolean addUsuario() {
		boolean data = firebaseService.agregar();
		return data;
	}
	
	@GetMapping("/obtener/{correo}")
	public Usuario obtenerUsuario(@PathVariable String correo) {
		Usuario  usu = firebaseService.obtenerUsuario(correo);
		return usu;
	}
}
