
package tiendaOnline.Pago;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tiendaOnline.Pago.model.Pago;
import tiendaOnline.Pago.repository.PagoRepository;

import java.time.LocalDate;

@Profile("dev")
@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (pagoRepository.count() == 0) {
            System.out.println("Generando datos de prueba para Pagos...");

            Faker faker = new Faker();

            String[] metodos = {"Tarjeta", "Transferencia", "PayPal"};

            for (int i = 0; i < 10; i++) {
                Pago pago = new Pago();

                pago.setIdPedido(faker.number().numberBetween(1L, 15L));

                pago.setIdCliente(faker.number().numberBetween(1L, 15L));

                pago.setMonto(faker.number().randomDouble(2, 10, 500));

                pago.setMetodoPago(metodos[faker.number().numberBetween(0, metodos.length)]);

                pago.setFechaPago(LocalDate.now().minusDays(faker.number().numberBetween(0, 30)));

                pagoRepository.save(pago);
            }

            System.out.println("más de 10 Pagos creados exitosamente en la BD");
        } else {
            System.out.println("La BD ya contiene pagos...");
        }

    }

}
