package tiendaOnline.auth.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiendaOnline.auth.assemblers.LoginResponseModelAssembler;
import tiendaOnline.auth.dto.LoginRequest;
import tiendaOnline.auth.dto.LoginResponse;
import tiendaOnline.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final LoginResponseModelAssembler assembler;

    public AuthController(AuthService authService,
                          LoginResponseModelAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(value = "/login",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(
                request.getUsername(),
                request.getPassword());

        return ResponseEntity.ok(assembler.toModel(response));
    }
}
