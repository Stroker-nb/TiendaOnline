package tiendaOnline.trabajador.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.trabajador.assemblers.TrabajadorModelAssembler;
import tiendaOnline.trabajador.dto.TrabajadorRequest;
import tiendaOnline.trabajador.model.Trabajador;
import tiendaOnline.trabajador.service.TrabajadorService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/trabajadores")

public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private TrabajadorModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Trabajador>> getAllTrabajadores() {
        List<EntityModel<Trabajador>> trabajadores = trabajadorService.getTrabajadores().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(trabajadores,
                linkTo(methodOn(TrabajadorController.class).getAllTrabajadores()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Trabajador> getTrabajadorByCodigo(@PathVariable String codigo) {
        Trabajador trabajador = trabajadorService.getTrabajador(Long.valueOf(codigo));
        return assembler.toModel(trabajador);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Trabajador>> guardarTrabajador(@Valid @RequestBody TrabajadorRequest request) {

        Trabajador newTrabajador = trabajadorService.saveTrabajador(request);

        return ResponseEntity
                .created(linkTo(methodOn(TrabajadorController.class)
                        .getTrabajadorByCodigo(String.valueOf(newTrabajador.getId())))
                        .toUri())
                .body(assembler.toModel(newTrabajador));
    }

}
