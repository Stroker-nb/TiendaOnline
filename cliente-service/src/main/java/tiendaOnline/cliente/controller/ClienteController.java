package tiendaOnline.cliente.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.cliente.assemblers.ClienteModelAssembler;
import tiendaOnline.cliente.dto.ClienteRequest;
import tiendaOnline.cliente.model.Cliente;
import tiendaOnline.cliente.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clientes")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cliente>> getAllClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.getClientes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).getAllClientes()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Cliente> getClienteByCodigo(@PathVariable String codigo) {
        Cliente resena = clienteService.getCliente(Long.valueOf(codigo));
        return assembler.toModel(resena);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Cliente>> guardar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());

        Cliente newCliente = clienteService.saveCliente(cliente);

        return ResponseEntity
                .created(linkTo(methodOn(ClienteController.class)
                        .getClienteByCodigo(String.valueOf(cliente.getId())))
                        .toUri())
                .body(assembler.toModel(newCliente));
    }

}
