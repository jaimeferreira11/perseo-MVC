package py.com.perseo.clientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.perseo.clientes.entities.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
