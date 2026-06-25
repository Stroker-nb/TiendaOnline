package tiendaOnline.sucursal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.sucursal.assemblers.SucursalModelAssembler;
import tiendaOnline.sucursal.dto.SucursalRequest;
import tiendaOnline.sucursal.model.Sucursal;
import tiendaOnline.sucursal.service.SucursalService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/sucursales")

public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.getSucursales().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalController.class).getAllSucursales()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Sucursal> getSucursalByCodigo(@PathVariable String codigo) {
        Sucursal sucursal = sucursalService.getSucursal(Long.valueOf(codigo));
        return assembler.toModel(sucursal);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Sucursal>> guardar(@Valid @RequestBody SucursalRequest request) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(request.getNombre());
        sucursal.setDireccion(request.getDireccion());

        Sucursal newSucursal = sucursalService.saveSucursal(sucursal);

        return ResponseEntity
                .created(linkTo(methodOn(SucursalController.class)
                        .getSucursalByCodigo(String.valueOf(sucursal.getId())))
                        .toUri())
                .body(assembler.toModel(newSucursal));
    }

}
