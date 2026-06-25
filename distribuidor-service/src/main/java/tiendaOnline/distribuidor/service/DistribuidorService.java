package tiendaOnline.distribuidor.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.distribuidor.dto.DistribuidorRequest;
import tiendaOnline.distribuidor.dto.ProductoResponse;
import tiendaOnline.distribuidor.exception.DistribuidorNoEncontradoException;
import tiendaOnline.distribuidor.model.Distribuidor;
import tiendaOnline.distribuidor.repository.DistribuidorRepository;



import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DistribuidorService {
    private static final Logger log = LoggerFactory.getLogger(DistribuidorService.class);

    @Autowired
    private DistribuidorRepository distribuidorRepository;

    private final WebClient.Builder webClientBuilder;

    @Value("${services.producto.url:http://PRODUCTO-SERVICE}")
    private String productoServiceUrl;

    public List<Distribuidor> getDistribuidores(){
        log.info("Listando todos los Distribuidores");
        return distribuidorRepository.findAll();
    }

    public Distribuidor getDistribuidor(Long id){
        log.info("Buscando Distribuidor con id: {}", id);
        return distribuidorRepository.findById(id)
                .orElseThrow(()-> new DistribuidorNoEncontradoException("Distribuidor con id " + id + "no encontrado"));
    }

    public Distribuidor saveDistribuidor(DistribuidorRequest request){

        ProductoResponse producto = webClientBuilder.build().get()
                .uri(productoServiceUrl + "/api/v1/productos/{id}", request.getIdProducto())
                .retrieve().bodyToMono(ProductoResponse.class).block();

        Distribuidor distribuidor = Distribuidor.builder()

                .idProducto(producto.id())
                .nombre(request.getNombre())
                .direccion(request.getDireccion())
                .build();

        log.info("Distribuidor guardado correctamente con id: {}", distribuidor.getId());
        return distribuidorRepository.save(distribuidor);
    }
}
