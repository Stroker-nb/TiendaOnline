
package tiendaOnline.resena.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.resena.controller.ResenaController;
import tiendaOnline.resena.model.Resena;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {

    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenaController.class).getResenaByCodigo(String.valueOf(resena.getId()))).withSelfRel(),
                linkTo(methodOn(ResenaController.class).getAllResenas()).withRel("reseñas"));
    }

}
