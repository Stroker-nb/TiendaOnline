
package tiendaOnline.producto;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.producto.model.Producto;
import tiendaOnline.producto.repository.ProductoRepository;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        if (productoRepository.count() == 0) {
            System.out.println("Generando datos falsos para productos...");

            for (int i = 0; i < 15; i++) {
                Producto producto = new Producto();

                producto.setNombre(faker.videoGame().title());
                producto.setValor(faker.number().numberBetween(1000,100000));


                productoRepository.save(producto);
            }

            System.out.println("Productos generados exitosamente en la base de datos.");
        } else {
            System.out.println("La base de datos ya contiene productos. Saltando Faker.");
        }

    }

}
