
package tiendaOnline.resena.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.resena.dto.ClienteResponse;
import tiendaOnline.resena.dto.ProductoResponse;
import tiendaOnline.resena.dto.ResenaRequest;
import tiendaOnline.resena.exception.ReseñaNoEncontradaException;
import tiendaOnline.resena.model.Resena;
import tiendaOnline.resena.repository.ResenaRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResenaService {
    private static final Logger log = LoggerFactory.getLogger(ResenaService.class);

    @Autowired
    private ResenaRepository resenaRepository;

    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE-SERVICE}")
    private String clienteServiceUrl;

    @Value("${services.producto.url:http://PRODUCTO-SERVICE}")
    private String productoServiceUrl;


    public List<Resena> getResenas(){
        log.info("Listando todas las reseñas");
        return resenaRepository.findAll();
    }

    public Resena getResena(Long id){
        log.info("Buscando reseña con id: {}", id);
        return resenaRepository.findById(id)
                .orElseThrow(()-> new ReseñaNoEncontradaException("Reseña con id " + id + " no encontrada"));
    }

    public Resena saveResena(ResenaRequest request) {
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{id}", request.getClienteId())
                .retrieve().bodyToMono(ClienteResponse.class).block();

        ProductoResponse producto = webClientBuilder.build().get()
                .uri(productoServiceUrl + "/api/v1/productos/{id}", request.getProductoId())
                .retrieve().bodyToMono(ProductoResponse.class).block();

        Resena resena = Resena.builder()
                .clienteId(cliente.id())
                .productoId(producto.id())
                .calificacion(request.getCalificacion())
                .comentario(request.getComentario())

                .build();

        log.info("Reseña guardada correctamente con id: {}", resena.getId());
        return resenaRepository.save(resena);
    }

}
