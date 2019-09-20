package com.essalud.main.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.essalud.main.entity.Usuario;
import com.essalud.main.service.UsuarioService;




@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@GetMapping("/usuarios")
	public String generarArchivo() {
		String salida ="";
		try {
			logger.info("Obteniendo datos de la base de datos");
			List<Usuario> usuarios = usuarioService.obtenerUsuarios();
			logger.info("Termine de obtener  datos de la base de datos");
			if (usuarioService.generarArchivo(usuarios)) {
				salida="Exito";
				logger.info("Termino de crear archivo correctamente");
			} else {
				salida="Error";
				logger.info("Error en el Servicio");
			}
		} catch (Exception e) {
			logger.info("Error en el Proceso en " + e.getMessage());
		}
		return salida;

	}
}
