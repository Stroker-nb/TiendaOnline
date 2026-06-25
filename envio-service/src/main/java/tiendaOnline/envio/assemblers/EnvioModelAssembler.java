
package tiendaOnline.envio.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.envio.controller.EnvioController;
import tiendaOnline.envio.model.Envio;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).getEnvioByCodigo(String.valueOf(envio.getId()))).withSelfRel(),
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("Envios"));
    }

}
