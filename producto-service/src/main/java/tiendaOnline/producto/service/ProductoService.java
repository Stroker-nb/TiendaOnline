package tiendaOnline.producto.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaOnline.producto.exception.ProductoNoEncontradoException;
import tiendaOnline.producto.model.Producto;
import tiendaOnline.producto.repository.ProductoRepository;

import java.util.List;

@Service
@Transactional

public class ProductoService {
    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos(){
        log.info("Listando todos los clientes");
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id){
        log.info("Buscando productos con id: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(()-> new ProductoNoEncontradoException("Producto con id " + id + "no encontrado"));
    }

    public Producto saveProducto(Producto producto){

        log.info("Producto guardado correctamente con id: {}", producto.getId());
        return productoRepository.save(producto);
    }
}
