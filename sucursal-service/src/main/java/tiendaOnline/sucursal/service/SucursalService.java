package tiendaOnline.sucursal.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendaOnline.sucursal.exception.SucursalNoEncontradoException;
import tiendaOnline.sucursal.model.Sucursal;
import tiendaOnline.sucursal.repository.SucursalRepository;

import java.util.List;

@Service
@Transactional

public class SucursalService {
    private static final Logger log = LoggerFactory.getLogger(SucursalService.class);

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> getSucursales(){
        log.info("Listando todos las Sucursales");
        return sucursalRepository.findAll();
    }

    public Sucursal getSucursal(Long id){
        log.info("Buscando Sucursal con id: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(()-> new SucursalNoEncontradoException("Sucursal con id " + id + "no encontrado"));
    }

    public Sucursal saveSucursal(Sucursal sucursal){

        log.info("Sucursal guardado correctamente con id: {}", sucursal.getId());
        return sucursalRepository.save(sucursal);
    }
}
