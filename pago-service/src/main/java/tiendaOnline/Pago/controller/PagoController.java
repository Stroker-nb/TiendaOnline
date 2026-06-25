
package tiendaOnline.Pago.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.Pago.assemblers.PagoModelAssembler;
import tiendaOnline.Pago.dto.PagoRequest;
import tiendaOnline.Pago.model.Pago;
import tiendaOnline.Pago.service.PagoService;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pago>> getAllPagos() {
        List<EntityModel<Pago>> resenas = pagoService.getPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resenas,
                linkTo(methodOn(PagoController.class).getAllPagos()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> createPago(
            @Valid @RequestBody PagoRequest request) {


        Pago newPago = pagoService.savePago(request);

        return ResponseEntity
                .created(linkTo(methodOn(PagoController.class)
                        .getPagoByCodigo(String.valueOf(newPago.getId())))
                        .toUri())
                .body(assembler.toModel(newPago));
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pago> getPagoByCodigo(@PathVariable String codigo) {
        Pago pago = pagoService.getPago(Long.valueOf(codigo));
        return assembler.toModel(pago);
    }
}
