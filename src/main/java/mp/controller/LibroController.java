package mp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Libro;
import mp.dto.LibroRequest;
import mp.exception.InvalidArgumentException;
import mp.service.LibroService;
import static mp.dto.DtoUtil.fromLibroRequestToEntity;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/libro")
public class LibroController {

	private final LibroService libroService;

	@GetMapping
	public ResponseEntity<List<Libro>> encontrarTodosLosLibros() {
		log.info("Buscando todos los libros");
		return ResponseEntity.ok(libroService.buscarTodos());
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<Libro>> encontrarLibroPorAtributo(@RequestParam Optional<String> nombre,
			@RequestParam Optional<String> id) {
		List<Libro> result = new ArrayList();

		if (nombre.isEmpty() && id.isEmpty()) {
			throw new InvalidArgumentException("Los argumentos de busqueda no son validos.");
		}

		if (!id.isEmpty()) {
			int libroId = Integer.parseInt(id.get());
			log.info("Buscando libro por id: " + libroId);
			result.add(libroService.buscarPorId(libroId));
		} else {
			log.info("Buscando libro por nombre: " + nombre.get());
			result = libroService.buscarPorParteDeNombre(nombre.get());
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<String> agregarLibro(@RequestBody LibroRequest libro) throws InvalidArgumentException {
		log.info("Agregando el libro: " + libro);
		libroService.crear(fromLibroRequestToEntity(libro));
		return ResponseEntity.status(HttpStatus.CREATED).body("Agregado el libro: " + libro.getNombre());
	}

	@PutMapping
	public ResponseEntity<String> actualizarLibro(@RequestBody LibroRequest libro) throws InvalidArgumentException {
		log.info("Editando el libro: " + libro);
		if (libro.getId() <= 0) {
			throw new InvalidArgumentException("El id del libro no es valido.");
		}
		libroService.actualizar(fromLibroRequestToEntity(libro));
		return ResponseEntity.status(HttpStatus.OK).body("Actualizado el libro: " + libro.getNombre());
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarLibro(@PathVariable int id) {
		log.info("Id del libro a eliminar: " + id);
		libroService.eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Libro eliminado");
	}


}
