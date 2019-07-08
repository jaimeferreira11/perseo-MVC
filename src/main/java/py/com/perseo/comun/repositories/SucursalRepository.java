package py.com.perseo.comun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.perseo.comun.entities.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

}