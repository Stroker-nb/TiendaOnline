package tiendaOnline.trabajador.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tiendaOnline.trabajador.dto.SucursalResponse;
import tiendaOnline.trabajador.dto.TrabajadorRequest;
import tiendaOnline.trabajador.exception.TrabajadorNoEncontradoException;
import tiendaOnline.trabajador.model.Trabajador;
import tiendaOnline.trabajador.repository.TrabajadorRepository;


import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class TrabajadorService {
    private static final Logger log = LoggerFactory.getLogger(TrabajadorService.class);

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    private final WebClient.Builder webClientBuilder;

    @Value("${services.sucursal.url:http://SUCURSAL-SERVICE}")
    private String sucursalServiceUrl;




    public List<Trabajador> getTrabajadores(){
        log.info("Listando todos los Trabajadores");
        return trabajadorRepository.findAll();
    }

    public Trabajador getTrabajador(Long id){
        log.info("Buscando trabajador con id: {}", id);
        return trabajadorRepository.findById(id)
                .orElseThrow(()-> new TrabajadorNoEncontradoException("Trabajador con id " + id + "no encontrado"));
    }

    public Trabajador saveTrabajador(TrabajadorRequest request){

        SucursalResponse sucursal = webClientBuilder.build().get()
                .uri(sucursalServiceUrl + "/api/v1/sucursales/{id}", request.getSucursalId())
                .retrieve().bodyToMono(SucursalResponse.class).block();

        Trabajador trabajador = Trabajador.builder()
                .idSucursal(sucursal.id())
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .telefono(request.getTelefono())

                .build();



        log.info("Trabajador guardado correctamente con id: {}", trabajador.getId());
        return trabajadorRepository.save(trabajador);
    }
}
