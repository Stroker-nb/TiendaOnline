package tiendaOnline.auth.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tiendaOnline.auth.controller.AuthController;
import tiendaOnline.auth.dto.LoginResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LoginResponseModelAssembler
        implements RepresentationModelAssembler<LoginResponse, EntityModel<LoginResponse>> {

    @Override
    public EntityModel<LoginResponse> toModel(LoginResponse response) {

        return EntityModel.of(response,
                linkTo(methodOn(AuthController.class).login(null))
                        .withSelfRel());
    }
}
