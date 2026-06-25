
package tiendaOnline.Pago.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.Pago.dto.ClienteResponse;
import tiendaOnline.Pago.dto.PagoRequest;
import tiendaOnline.Pago.dto.PedidoResponse;
import tiendaOnline.Pago.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaOnline.Pago.exception.PagoNoEncontradoException;
import tiendaOnline.Pago.repository.PagoRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE-SERVICE}")
    private String clienteServiceUrl;

    @Value("${services.pedido.url:http://PEDIDO-SERVICE}")
    private String pedidoServiceUrl;




    public List<Pago> getPagos(){
        log.info("Listando todos los pagos");
        return pagoRepository.findAll();
    }

    public Pago savePago(PagoRequest request){

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{id}", request.getIdCliente())
                .retrieve().bodyToMono(ClienteResponse.class).block();

        PedidoResponse pedido = webClientBuilder.build().get()
                .uri(pedidoServiceUrl + "/api/v1/pedidos/{id}", request.getIdPedido())
                .retrieve().bodyToMono(PedidoResponse.class).block();

        Pago pago = Pago.builder()
                .idCliente(cliente.id())
                .idPedido(pedido.id())
                .metodoPago(request.getMetodoPago())
                .fechaPago(request.getFechaPago())
                .monto(request.getMonto())


                .build();

        log.info("Reseña guardada correctamente con id: {}", pago.getId());
        return pagoRepository.save(pago);
    }

    public Pago getPago(Long id) {
        log.info("Buscando pago con id: {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException("No existe el pago con ID: " + id));
    }

}
