
package tiendaOnline.cliente;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.cliente.model.Cliente;
import tiendaOnline.cliente.repository.ClienteRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (clienteRepository.count() == 0) {
            System.out.println("Generando datos falsos para cliente...");

            for (int i = 0; i < 15; i++) {
                Cliente cliente = new Cliente();

                cliente.setNombre(faker.breakingBad().character());
                cliente.setCorreo(faker.internet().emailAddress());
                cliente.setTelefono(faker.phoneNumber().cellPhone());

                clienteRepository.save(cliente);
            }

            System.out.println("clientes generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene clientes. Saltando Faker.");
        }

    }

}
