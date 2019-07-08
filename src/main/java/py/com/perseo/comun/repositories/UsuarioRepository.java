package py.com.perseo.comun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.perseo.comun.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}