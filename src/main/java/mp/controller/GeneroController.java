package mp.controller;

import java.util.List;
import java.util.Optional;

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
import mp.domain.Genero;
import mp.dto.GeneroRequest;
import mp.exception.InvalidArgumentException;
import mp.service.GeneroService;
import static mp.dto.DtoUtil.fromGeneroRequestToEntity;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/genero")
public class GeneroController {
	private final GeneroService generoService;
	
	@GetMapping
	public ResponseEntity<List<Genero>> listarTodosLosGeneros(){
		log.info("Buscando todos los generos");
		return ResponseEntity.ok(generoService.buscarTodos());
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<Genero> encontrarLibroPorAtributo(@RequestParam Optional<String> nombre, 
															@RequestParam Optional<String> id){
		Genero result;
		
		if(nombre.isEmpty() && id.isEmpty()) {
			throw new InvalidArgumentException("Los argumentos de busqueda no son validos.");
		}
		
		if(!id.isEmpty()) {
			int generoId = Integer.parseInt(id.get());
			result = generoService.buscarPorId(generoId);
			log.info("Buscando genero por id: " + generoId);	
		} else {
			result = generoService.buscarPorNombre(nombre.get());
			log.info("Buscando genero por nombre: " + nombre.get());		
		} 		
		
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<String> crearGenero(@RequestBody GeneroRequest genero){
		log.info("Agregando el genero: " + genero.getNombre());
				
		generoService.crear(fromGeneroRequestToEntity(genero));
		return ResponseEntity.status(HttpStatus.CREATED).body("Agregado el genero: " + genero.getNombre());
		
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarGenero(@RequestBody GeneroRequest genero){
		log.info("Actualizando el genero con nombre: " + genero.getNombre());
		if(genero.getId() <= 0) {
			throw new InvalidArgumentException("El id del genero no es valido.");
		}
		generoService.actualizar(fromGeneroRequestToEntity(genero));
		return ResponseEntity.status(HttpStatus.OK).body("Actualizado el genero: " + genero.getNombre());
		
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarGenero(@PathVariable int id){
		log.info("Eliminando el genero con id: " + id);
		generoService.eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Genero eliminado");
		
	}
	
		

}
