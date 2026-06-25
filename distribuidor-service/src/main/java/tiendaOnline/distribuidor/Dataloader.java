
package tiendaOnline.distribuidor;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.distribuidor.model.Distribuidor;
import tiendaOnline.distribuidor.repository.DistribuidorRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private DistribuidorRepository distribuidorRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (distribuidorRepository.count() == 0) {
            System.out.println("Generando datos falsos para distribuidores...");

            for (int i = 0; i < 15; i++) {
                Distribuidor distribuidor = new Distribuidor();

                distribuidor.setIdProducto(faker.number().numberBetween(1L, 15L));
                distribuidor.setNombre(faker.company().name());
                distribuidor.setDireccion(faker.country().name());

                distribuidorRepository.save(distribuidor);
            }

            System.out.println("Distribuidores generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene distribuidores. Saltando Faker.");
        }

    }

}
