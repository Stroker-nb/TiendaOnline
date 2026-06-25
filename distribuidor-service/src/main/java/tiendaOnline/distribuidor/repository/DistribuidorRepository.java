package tiendaOnline.distribuidor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendaOnline.distribuidor.model.Distribuidor;

@Repository
public interface DistribuidorRepository extends JpaRepository<Distribuidor, Long> {

}
