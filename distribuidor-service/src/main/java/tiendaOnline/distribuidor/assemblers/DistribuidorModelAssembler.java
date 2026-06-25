
package tiendaOnline.distribuidor.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.distribuidor.controller.DistribuidorController;
import tiendaOnline.distribuidor.model.Distribuidor;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DistribuidorModelAssembler implements RepresentationModelAssembler<Distribuidor, EntityModel<Distribuidor>> {

    @Override
    public EntityModel<Distribuidor> toModel(Distribuidor distribuidor) {
        return EntityModel.of(distribuidor,
                linkTo(methodOn(DistribuidorController.class).getDistribuidorByCodigo(String.valueOf(distribuidor.getId()))).withSelfRel(),
                linkTo(methodOn(DistribuidorController.class).getAllDistribuidores()).withRel("Distribuidores"));
    }

}
