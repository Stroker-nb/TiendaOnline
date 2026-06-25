
package tiendaOnline.pedido.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.pedido.controller.PedidoController;
import tiendaOnline.pedido.model.Pedido;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).getPedidoByCodigo(String.valueOf(pedido.getId()))).withSelfRel(),
                linkTo(methodOn(PedidoController.class).getAllPedidos()).withRel("pedidos"));
    }

}
