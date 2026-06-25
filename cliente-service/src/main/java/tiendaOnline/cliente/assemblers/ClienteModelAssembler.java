
package tiendaOnline.cliente.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.cliente.controller.ClienteController;
import tiendaOnline.cliente.model.Cliente;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).getClienteByCodigo(String.valueOf(cliente.getId()))).withSelfRel(),
                linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"));
    }

}
