package tiendaOnline.producto.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendaOnline.producto.assemblers.ProductoModelAssembler;
import tiendaOnline.producto.dto.ProductoRequest;
import tiendaOnline.producto.model.Producto;
import tiendaOnline.producto.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/productos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProducto() {
        List<EntityModel<Producto>> clientes = productoService.getProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(ProductoController.class).getAllProducto()).withSelfRel());
    }

    @GetMapping(value = "/{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoByCodigo(@PathVariable String codigo) {
        Producto resena = productoService.getProducto(Long.valueOf(codigo));
        return assembler.toModel(resena);
    }

    @PostMapping
    public ResponseEntity<EntityModel <Producto>> getAllProductos(@Valid @RequestBody ProductoRequest request) {
        Producto producto = new Producto();

        producto.setNombre(request.getNombre());
        producto.setValor(request.getValor());

        Producto newProducto = productoService.saveProducto(producto);

        return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class)
                        .getProductoByCodigo(String.valueOf(producto.getId())))
                        .toUri())
                .body(assembler.toModel(newProducto));
    }

}
