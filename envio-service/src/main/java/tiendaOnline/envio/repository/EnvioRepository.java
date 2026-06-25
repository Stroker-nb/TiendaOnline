package tiendaOnline.envio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendaOnline.envio.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

}
