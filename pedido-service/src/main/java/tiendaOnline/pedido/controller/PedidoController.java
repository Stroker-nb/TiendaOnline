
package tiendaOnline.pedido.controller;

import jakarta.validation.Valid;
import tiendaOnline.pedido.assemblers.PedidoModelAssembler;
import tiendaOnline.pedido.dto.PedidoRequest;
import tiendaOnline.pedido.model.Pedido;
import tiendaOnline.pedido.service.PedidoService;

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
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() {
        List<EntityModel<Pedido>> resenas = pedidoService.getPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resenas,
                linkTo(methodOn(PedidoController.class).getAllPedidos()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pedido> getPedidoByCodigo(@PathVariable String codigo) {
        Pedido pedido = pedidoService.getPedido(Long.valueOf(codigo));
        return assembler.toModel(pedido);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> createResena(
            @Valid @RequestBody PedidoRequest request) {



        Pedido newPedido = pedidoService.savePedido(request);

        return ResponseEntity
                .created(linkTo(methodOn(PedidoController.class)
                        .getPedidoByCodigo(String.valueOf(newPedido.getId())))
                        .toUri())
                .body(assembler.toModel(newPedido));
    }



}
