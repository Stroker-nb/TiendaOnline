package tiendaOnline.cliente;

import tiendaOnline.cliente.exception.ClienteNoEncontradoException;
import tiendaOnline.cliente.model.Cliente;
import tiendaOnline.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tiendaOnline.cliente.service.ClienteService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	@Test
	void getCliente_debeRetornarClienteCuandoExiste() {
		Cliente entidad = new Cliente();
		entidad.setId(1L);
		entidad.setNombre("Juan Pérez");
		entidad.setCorreo("juan@correo.com");
		entidad.setTelefono("912345678");

		when(clienteRepository.findById(1L)).thenReturn(Optional.of(entidad));

		Cliente resultado = clienteService.getCliente(1L);

		assertEquals(1L, resultado.getId());
		assertEquals("Juan Pérez", resultado.getNombre());
		verify(clienteRepository).findById(1L);
	}

	@Test
	void getCliente_debeLanzarExcepcionCuandoNoExiste() {
		when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(ClienteNoEncontradoException.class, () -> clienteService.getCliente(99L));
		verify(clienteRepository).findById(99L);
	}

	@Test
	void getClientes_debeRetornarTodosLosClientes() {
		Cliente c1 = new Cliente();
		c1.setId(1L);
		c1.setNombre("Juan Pérez");
		c1.setCorreo("juan@correo.com");
		c1.setTelefono("912345678");

		Cliente c2 = new Cliente();
		c2.setId(2L);
		c2.setNombre("María López");
		c2.setCorreo("maria@correo.com");
		c2.setTelefono("987654321");

		when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));

		List<Cliente> resultado = clienteService.getClientes();

		assertEquals(2, resultado.size());
		verify(clienteRepository).findAll();
	}

	@Test
	void getClientes_debeRetornarListaVaciaCuandoNoHayClientes() {
		when(clienteRepository.findAll()).thenReturn(List.of());

		List<Cliente> resultado = clienteService.getClientes();

		assertTrue(resultado.isEmpty());
		verify(clienteRepository).findAll();
	}

	@Test
	void saveCliente_debeRetornarClienteGuardado() {
		Cliente nuevo = new Cliente();
		nuevo.setNombre("Carlos Ruiz");
		nuevo.setCorreo("carlos@correo.com");
		nuevo.setTelefono("911222333");

		Cliente guardado = new Cliente();
		guardado.setId(3L);
		guardado.setNombre("Carlos Ruiz");
		guardado.setCorreo("carlos@correo.com");
		guardado.setTelefono("911222333");

		when(clienteRepository.save(nuevo)).thenReturn(guardado);

		Cliente resultado = clienteService.saveCliente(nuevo);

		assertEquals(3L, resultado.getId());
		assertEquals("Carlos Ruiz", resultado.getNombre());
		verify(clienteRepository).save(nuevo);
	}

	@Test
	void saveCliente_debeInvocarRepositorioUnaVezSola() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Test");
		cliente.setCorreo("test@correo.com");
		cliente.setTelefono("912345678");

		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

		clienteService.saveCliente(cliente);

		verify(clienteRepository, times(1)).save(cliente);
		verifyNoMoreInteractions(clienteRepository);
	}
}