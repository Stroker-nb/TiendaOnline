
package tiendaOnline.pedido;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.pedido.model.Pedido;
import tiendaOnline.pedido.repository.PedidoRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (pedidoRepository.count() == 0) {
            System.out.println("Generando datos falsos para Pedidos...");

            for (int i = 0; i < 15; i++) {
                Pedido pedido = new Pedido();

                pedido.setProductoId(faker.number().numberBetween(1L, 15L));
                pedido.setClienteId(faker.number().numberBetween(1L, 15L));
                pedido.setMonto(faker.number().numberBetween(10000, 100000));


                pedidoRepository.save(pedido);
            }

            System.out.println("Pedidos generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene Pedidos. Saltando Faker.");
        }

    }

}
