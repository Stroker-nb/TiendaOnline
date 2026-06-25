
package tiendaOnline.resena.controller;

import jakarta.validation.Valid;
import tiendaOnline.resena.assemblers.ResenaModelAssembler;
import tiendaOnline.resena.dto.ResenaRequest;
import tiendaOnline.resena.model.Resena;
import tiendaOnline.resena.service.ResenaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/Resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;
    @Autowired
    private ResenaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Resena>> getAllResenas() {
        List<EntityModel<Resena>> resenas = resenaService.getResenas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resenas,
                linkTo(methodOn(ResenaController.class).getAllResenas()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Resena> getResenaByCodigo(@PathVariable String codigo) {
        Resena resena = resenaService.getResena(Long.valueOf(codigo));
        return assembler.toModel(resena);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resena>> createResena(
            @Valid @RequestBody ResenaRequest request) {

        Resena newResena = resenaService.saveResena(request);

        return ResponseEntity
                .created(linkTo(methodOn(ResenaController.class)
                        .getResenaByCodigo(String.valueOf(newResena.getId())))
                        .toUri())
                .body(assembler.toModel(newResena));
    }



}
