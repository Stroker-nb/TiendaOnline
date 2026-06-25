package tiendaOnline.auth.service;

import tiendaOnline.auth.dto.LoginResponse;
import tiendaOnline.auth.exception.AuthException;
import tiendaOnline.auth.model.Usuario;
import tiendaOnline.auth.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new AuthException("Password incorrecta");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol());

        return new LoginResponse("Usuario autorizado", token, usuario.getUsername(), usuario.getRol());
    }
}
