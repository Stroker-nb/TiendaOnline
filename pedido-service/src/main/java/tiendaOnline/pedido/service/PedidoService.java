
package tiendaOnline.pedido.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.pedido.dto.ClienteResponse;
import tiendaOnline.pedido.dto.PedidoRequest;
import tiendaOnline.pedido.dto.ProductoResponse;
import tiendaOnline.pedido.exception.PedidoNoEncontradoException;
import tiendaOnline.pedido.model.Pedido;
import tiendaOnline.pedido.repository.PedidoRepository;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE-SERVICE}")
    private String clienteServiceUrl;

    @Value("${services.producto.url:http://PRODUCTO-SERVICE}")
    private String productoServiceUrl;



    public List<Pedido> getPedidos(){
        log.info("Listando todas las pedidos");
        return pedidoRepository.findAll();
    }

    public Pedido getPedido(Long id){
        log.info("Buscando reseña con id: {}", id);
        return pedidoRepository.findById(id)
                .orElseThrow(()-> new PedidoNoEncontradoException("Reseña con id " + id + " no encontrada"));
    }

    public Pedido savePedido(PedidoRequest request){

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{id}", request.getClienteId())
                .retrieve().bodyToMono(ClienteResponse.class).block();

        ProductoResponse producto = webClientBuilder.build().get()
                .uri(productoServiceUrl + "/api/v1/productos/{id}", request.getProductoId())
                .retrieve().bodyToMono(ProductoResponse.class).block();

        Pedido pedido = Pedido.builder()
                .clienteId(cliente.id())
                .productoId(producto.id())
                .monto(request.getMonto())
                .build();



        log.info("Pedido guardada correctamente con id: {}", pedido.getId());
        return pedidoRepository.save(pedido);
    }

}
