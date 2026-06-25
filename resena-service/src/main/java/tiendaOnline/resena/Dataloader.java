
package tiendaOnline.resena;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tiendaOnline.resena.model.Resena;
import tiendaOnline.resena.repository.ResenaRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (resenaRepository.count() == 0) {
            System.out.println("Generando datos falsos para Reseñas...");

            for (int i = 0; i < 15; i++) {
                Resena resena = new Resena();

                resena.setProductoId(faker.number().numberBetween(1L, 15L));
                resena.setClienteId(faker.number().numberBetween(1L, 15L));
                resena.setCalificacion(faker.number().numberBetween(1, 5));
                resena.setComentario(faker.lorem().sentence());

                resenaRepository.save(resena);
            }

            System.out.println("Reseñas generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene reseñas. Saltando Faker.");
        }

    }

}
