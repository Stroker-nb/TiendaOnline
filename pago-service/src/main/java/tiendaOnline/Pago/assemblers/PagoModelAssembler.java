
package tiendaOnline.Pago.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import tiendaOnline.Pago.controller.PagoController;
import tiendaOnline.Pago.model.Pago;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).getPagoByCodigo(String.valueOf(pago.getId()))).withSelfRel(),
                linkTo(methodOn(PagoController.class).getAllPagos()).withRel("pagos"));
    }

}
