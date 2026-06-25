
package tiendaOnline.trabajador;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.trabajador.model.Trabajador;
import tiendaOnline.trabajador.repository.TrabajadorRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (trabajadorRepository.count() == 0) {
            System.out.println("Generando datos falsos para trabajador...");

            for (int i = 0; i < 15; i++) {
                Trabajador trabajador = new Trabajador();

                trabajador.setIdSucursal(faker.number().numberBetween(1L, 15L));
                trabajador.setNombre(faker.sonicTheHedgehog().character());
                trabajador.setCorreo(faker.internet().emailAddress());
                trabajador.setTelefono(faker.phoneNumber().cellPhone());

                trabajadorRepository.save(trabajador);
            }

            System.out.println("trabajadores generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene trabajadores. Saltando Faker.");
        }

    }

}
