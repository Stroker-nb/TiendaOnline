
package tiendaOnline.sucursal;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.sucursal.model.Sucursal;
import tiendaOnline.sucursal.repository.SucursalRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (sucursalRepository.count() == 0) {
            System.out.println("Generando datos falsos para sucursales...");

            for (int i = 0; i < 15; i++) {
                Sucursal sucursal = new Sucursal();

                sucursal.setNombre(faker.funnyName().name());
                sucursal.setDireccion(faker.country().name());

                sucursalRepository.save(sucursal);
            }

            System.out.println("Sucursales generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene sucursales. Saltando Faker.");
        }

    }

}
