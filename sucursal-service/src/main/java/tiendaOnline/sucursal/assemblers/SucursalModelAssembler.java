
package tiendaOnline.sucursal.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.sucursal.controller.SucursalController;
import tiendaOnline.sucursal.model.Sucursal;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).getSucursalByCodigo(String.valueOf(sucursal.getId()))).withSelfRel(),
                linkTo(methodOn(SucursalController.class).getAllSucursales()).withRel("sucursales"));
    }

}
