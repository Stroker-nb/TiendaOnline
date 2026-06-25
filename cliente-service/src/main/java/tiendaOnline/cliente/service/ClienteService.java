package tiendaOnline.cliente.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaOnline.cliente.exception.ClienteNoEncontradoException;
import tiendaOnline.cliente.model.Cliente;
import tiendaOnline.cliente.repository.ClienteRepository;

import java.util.List;

@Service
@Transactional

public class ClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getClientes(){
        log.info("Listando todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente getCliente(Long id){
        log.info("Buscando cliente con id: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(()-> new ClienteNoEncontradoException("Cliente con id " + id + "no encontrado"));
    }

    public Cliente saveCliente(Cliente cliente){

        log.info("Cliente guardado correctamente con id: {}", cliente.getId());
        return clienteRepository.save(cliente);
    }
}
