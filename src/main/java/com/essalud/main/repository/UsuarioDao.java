package com.essalud.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.essalud.main.entity.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario,Long> {
	
	@Query(value = "select * "
			+ "from users U  WHERE U.STDO=1 order by u.iduser",nativeQuery = true)
	List<Usuario> getUsuarios();

}
