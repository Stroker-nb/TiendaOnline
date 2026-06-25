package tiendaOnline.envio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.envio.assemblers.EnvioModelAssembler;
import tiendaOnline.envio.dto.EnvioRequest;
import tiendaOnline.envio.model.Envio;
import tiendaOnline.envio.service.EnvioService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/envios")

public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Envio>> getAllEnvios() {
        List<EntityModel<Envio>> envios = envioService.getEnvios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(envios,
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Envio> getEnvioByCodigo(@PathVariable String codigo) {
        Envio envio = envioService.getEnvio(Long.valueOf(codigo));
        return assembler.toModel(envio);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Envio>> guardarEnvios(@Valid @RequestBody EnvioRequest request) {


        Envio newEnvio = envioService.saveEnvio(request);

        return ResponseEntity
                .created(linkTo(methodOn(EnvioController.class)
                        .getEnvioByCodigo(String.valueOf(request.getId())))
                        .toUri())
                .body(assembler.toModel(newEnvio));
    }

}
