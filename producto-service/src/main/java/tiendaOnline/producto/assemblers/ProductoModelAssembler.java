
package tiendaOnline.producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.producto.controller.ProductoController;
import tiendaOnline.producto.model.Producto;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoByCodigo(String.valueOf(producto.getId()))).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProducto()).withRel("productos"));
    }

}
