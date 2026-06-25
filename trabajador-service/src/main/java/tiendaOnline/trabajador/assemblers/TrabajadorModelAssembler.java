
package tiendaOnline.trabajador.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.trabajador.controller.TrabajadorController;
import tiendaOnline.trabajador.model.Trabajador;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TrabajadorModelAssembler implements RepresentationModelAssembler<Trabajador, EntityModel<Trabajador>> {

    @Override
    public EntityModel<Trabajador> toModel(Trabajador trabajador) {
        return EntityModel.of(trabajador,
                linkTo(methodOn(TrabajadorController.class).getTrabajadorByCodigo(String.valueOf(trabajador.getId()))).withSelfRel(),
                linkTo(methodOn(TrabajadorController.class).getAllTrabajadores()).withRel("trabajadores"));
    }

}
