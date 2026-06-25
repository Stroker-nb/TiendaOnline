
package tiendaOnline.envio;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.envio.model.Envio;
import tiendaOnline.envio.repository.EnvioRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private EnvioRepository envioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (envioRepository.count() == 0) {
            System.out.println("Generando datos falsos para envio...");

            for (int i = 0; i < 15; i++) {
                Envio envio = new Envio();

                envio.setClienteId(faker.number().numberBetween(1L, 15L));
                envio.setPedidoId(faker.number().numberBetween(1L, 15L));
                envio.setDireccion(faker.country().capital());

                envioRepository.save(envio);
            }

            System.out.println("envio generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene envios. Saltando Faker.");
        }

    }

}
