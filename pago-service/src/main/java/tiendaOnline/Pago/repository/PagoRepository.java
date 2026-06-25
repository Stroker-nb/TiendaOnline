
package tiendaOnline.Pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiendaOnline.Pago.model.Pago;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{

}
