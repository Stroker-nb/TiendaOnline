package tiendaOnline.envio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.envio.dto.ClienteResponse;
import tiendaOnline.envio.dto.EnvioRequest;
import tiendaOnline.envio.dto.PedidoResponse;
import tiendaOnline.envio.exception.EnvioNoEncontradoException;
import tiendaOnline.envio.model.Envio;
import tiendaOnline.envio.repository.EnvioRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnvioService {
    private static final Logger log = LoggerFactory.getLogger(EnvioService.class);

    @Autowired
    private EnvioRepository envioRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE-SERVICE}")
    private String clienteServiceUrl;

    @Value("${services.producto.url:http://PEDIDO-SERVICE}")
    private String pedidoServiceUrl;



    public List<Envio> getEnvios(){
        log.info("Listando todos los Envios");
        return envioRepository.findAll();
    }

    public Envio getEnvio(Long id){
        log.info("Buscando Envio con id: {}", id);
        return envioRepository.findById(id)
                .orElseThrow(()-> new EnvioNoEncontradoException("Envio con id " + id + "no encontrado"));
    }

    public Envio saveEnvio(EnvioRequest request){
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{id}", request.getClienteId())
                .retrieve().bodyToMono(ClienteResponse.class).block();

        PedidoResponse pedido = webClientBuilder.build().get()
                .uri(pedidoServiceUrl + "/api/v1/pedidos/{id}", request.getPedidoId())
                .retrieve().bodyToMono(PedidoResponse.class).block();

        Envio envio = Envio.builder()
                .clienteId(cliente.id())
                .pedidoId(pedido.id())
                .direccion(request.getDireccion())
                .build();

        log.info("Envio guardado correctamente con id: {}", envio.getId());
        return envioRepository.save(envio);
    }
}
