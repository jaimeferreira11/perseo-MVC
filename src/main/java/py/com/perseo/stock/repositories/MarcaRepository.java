package py.com.perseo.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.perseo.stock.entities.Marca;

public interface MarcaRepository  extends JpaRepository<Marca, Integer> {

}
