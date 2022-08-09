package mp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Libro;
import mp.domain.Prestaciones;
import mp.domain.Usuario;
import mp.dto.PrestacionesRequest;
import mp.dto.PrestacionesResponse;
import mp.exception.InvalidArgumentException;
import mp.exception.InvalidPetitionException;
import mp.service.LibroService;
import mp.service.PrestacionesService;
import mp.service.UsuarioService;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/prestaciones")
public class PrestacionesController {
	private PrestacionesService prestacionesService;
	private LibroService libroService;
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<PrestacionesResponse>> listarTodasPrestacionesActivas() {

		log.info("Listando todas las prestaciones activas.");

		return ResponseEntity.ok(prestacionesService.listarTodasPrestacionesActivas());
	}

	@GetMapping("/historial")
	public ResponseEntity<List<PrestacionesResponse>> listarTodasPrestaciones() {

		log.info("Listando todas las prestaciones.");

		return ResponseEntity.ok(prestacionesService.listarTodasPrestaciones());
	}
	
	@GetMapping("/historial/buscar")
	public ResponseEntity<List<PrestacionesResponse>> listarTodasPrestacionesPorAtributo(
			@RequestParam Optional<String> usuarioId,
			@RequestParam Optional<String> libroId) {
		List<PrestacionesResponse> result = new ArrayList<>();
		
		if (usuarioId.isEmpty() && libroId.isEmpty()) {
			throw new InvalidArgumentException("Los argumentos de busqueda no son validos.");
		}
		
		int idValue = 0;
		
		if (!usuarioId.isEmpty()) {
			idValue = Integer.parseInt(usuarioId.get());
			log.info("Buscando prestaciones por id de usuario: " + idValue);
			result = prestacionesService.historialPrestacionesPorUsuarioId(idValue);
		} else {
			idValue = Integer.parseInt(libroId.get());
			log.info("Buscando prestaciones por id de libro: " + idValue);
			result = prestacionesService.historialPrestacionesPorLibroId(idValue);
		}

		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<List<PrestacionesResponse>> listarTodasPrestacionesActivasPorAtributo(@RequestParam Optional<String> usuarioId,
			@RequestParam Optional<String> libroId) {
		List<PrestacionesResponse> result = new ArrayList<>();
		
		if (usuarioId.isEmpty() && libroId.isEmpty()) {
			throw new InvalidArgumentException("Los argumentos de busqueda no son validos.");
		}
		
		int idValue = 0;
		
		if (!usuarioId.isEmpty()) {
			idValue = Integer.parseInt(usuarioId.get());
			log.info("Buscando prestaciones activas por id de usuario: " + idValue);
			result = prestacionesService.prestacionesActivasPorUsuarioId(idValue);
		} else {
			idValue = Integer.parseInt(libroId.get());
			log.info("Buscando prestaciones activas por id de libro: " + idValue);
			result = prestacionesService.prestacionesActivasPorLibroId(idValue);
		}

		return ResponseEntity.ok(result);
	}
	
		
	@PostMapping
	public ResponseEntity<String> agregarLibro(@RequestBody PrestacionesRequest prestacionesReq) {
		
		//Revisamos que los parametros de entrada sean correctos
		
		Libro libro = new Libro();
		libro.setId(prestacionesReq.getLibroId());
		if(!libroService.existeLibroPorId(libro.getId())) {
			throw new InvalidArgumentException("El libro no se ha encontrado.");
		}
				
		Optional<Usuario> usuario = usuarioService.buscarUsuarioPorUsername(prestacionesReq.getUsername());
		if(usuario.isEmpty()) {
			throw new InvalidArgumentException("El usuario ingresado no se ha encontrado.");
		}
		
		
		// Verificamos que el prestamo sea posible y este permitido
		
		// Un usuario no puede retirar dos volumenes del mismo libro
		if(prestacionesService.hayPrestacionActiva(libro, usuario.get())) {
			throw new InvalidPetitionException("El usuario " + usuario.get().getUsername() 
					+ " ya tiene una copia del libro. Por ende, no puede retirar otra.");
		}
				
		
		//Verificar que haya stock disponible del libro
		if(!libroService.hayStockDisponible(libro.getId())) {
			throw new InvalidPetitionException("No hay stock disponible del libro demandado.");
		}
		
		
		log.info("Creando nueva prestacion del libro de id " + libro.getId() + " por el usuario " + usuario.get().getUsername());
		prestacionesService.prestarLibro(libro, usuario.get());
		return ResponseEntity.status(HttpStatus.CREATED).body("Creado el prestamo a: " + usuario.get().getUsername());
	}
	
	
	@PutMapping("/devolver")
	public ResponseEntity<String> devolverLibro(@RequestBody PrestacionesRequest prestacionesReq){
		
		if(prestacionesReq.getId() > 0) {
			
			prestacionesService.devolucionLibroById(prestacionesReq.getId());
			
		} else {
			
			if((prestacionesReq.getLibroId() <= 0) || (prestacionesReq.getUsername().isBlank())) {
				throw new InvalidArgumentException("Los argumentos de busqueda no son validos.");
			}
			
			int libroId = prestacionesReq.getLibroId();
			String username = prestacionesReq.getUsername();
			
			if(!libroService.existeLibroPorId(libroId)) {
				throw new InvalidArgumentException("El libro no se ha encontrado.");
			}
			
			int usuarioId = usuarioService.buscarIdUsuarioPorUsername(username);
			if(usuarioId == 0) {
				throw new InvalidArgumentException("El usuario ingresado no se ha encontrado.");
			}
			
			prestacionesService.devolucionLibroByLibroIdAndUsuarioId(libroId, usuarioId);
		}
				
		return ResponseEntity.ok("Libro devuelto con exito.");
	}

	
	
}
