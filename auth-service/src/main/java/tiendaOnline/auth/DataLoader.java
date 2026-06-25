package tiendaOnline.auth;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tiendaOnline.auth.model.Usuario;
import tiendaOnline.auth.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {

        Faker faker = new Faker();

        // evitar insertar siempre el mismo usuario al reiniciar
        if(usuarioRepository.count() == 0){

            Usuario usuario = new Usuario();

            usuario.setUsername(faker.name().username());


            usuario.setPassword("1234");
            usuario.setRol(faker.construction().roles());

            usuarioRepository.save(usuario);

            System.out.println("Usuario creado");
        }
    }
}
