package tiendaOnline.distribuidor.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.distribuidor.assemblers.DistribuidorModelAssembler;
import tiendaOnline.distribuidor.dto.DistribuidorRequest;
import tiendaOnline.distribuidor.model.Distribuidor;
import tiendaOnline.distribuidor.service.DistribuidorService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/Distribuidores")

public class DistribuidorController {

    @Autowired
    private DistribuidorService distribuidorService;

    @Autowired
    private DistribuidorModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Distribuidor>> getAllDistribuidores() {
        List<EntityModel<Distribuidor>> distribuidores = distribuidorService.getDistribuidores().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(distribuidores,
                linkTo(methodOn(DistribuidorController.class).getAllDistribuidores()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Distribuidor> getDistribuidorByCodigo(@PathVariable String codigo) {
        Distribuidor distribuidor = distribuidorService.getDistribuidor(Long.valueOf(codigo));
        return assembler.toModel(distribuidor);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Distribuidor>> guardarDistribuidor(@Valid @RequestBody DistribuidorRequest request) {

        Distribuidor newDistribuidor = distribuidorService.saveDistribuidor(request);

        return ResponseEntity
                .created(linkTo(methodOn(DistribuidorController.class)
                        .getDistribuidorByCodigo(String.valueOf(request.getId())))
                        .toUri())
                .body(assembler.toModel(newDistribuidor));
    }

}
