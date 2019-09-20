package com.essalud.main.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essalud.main.entity.Usuario;
import com.essalud.main.entity.Usuario_Datos;
import com.essalud.main.repository.UsuarioDao;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Usuario> obtenerUsuarios() {
		return usuarioDao.getUsuarios();
	}

	public boolean generarArchivo(List<Usuario> listusuarios) {
		boolean estado = false;
		if (listusuarios != null) {
			String ruta = "C:\\Users\\gherson.perez\\Documents\\workspace-spring-tool-suite-4-4.2.2.RELEASE\\AppFlutterBackend\\datos\\archivo.txt";
			File archivo = new File(ruta);
			if (archivo.exists()) {
				leerArray(listusuarios);
				logger.info("El fichero de texto ya estaba creado.");
			} else {
				leerArray(listusuarios);
				logger.info("Acabo de crear el fichero de texto.");
			}
			estado = true;

		}
		return estado;
	}

	public void leerArray(List<Usuario> listaUsuario) {
		String ruta = "C:\\Users\\gherson.perez\\Documents\\workspace-spring-tool-suite-4-4.2.2.RELEASE\\AppFlutterBackend\\datos\\archivo.txt";
		File archivo = new File(ruta);
		logger.info(archivo.getAbsolutePath());
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(archivo));
			for (Usuario u : listaUsuario) {
				if (u.getUsuario_datos() == null) {
					Usuario_Datos ud = new Usuario_Datos();
					ud.setCorreo("");
					ud.setId(u.getId());
					u.setUsuario_datos(ud);
				}
				bw.write(u.getId().toString() + "\t" + u.getNombres() + "\t" + u.getA_paterno() + "\t"
						+ u.getA_materno() + "\t" + u.getUser_name() + "\t" + u.getUsuario_datos().getCorreo() + "\t"
						+ u.getContrasena() + "\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
