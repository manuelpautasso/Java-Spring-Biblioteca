package mp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mp.domain.Genero;
import mp.exception.EntityNotFoundException;
import mp.exception.InvalidArgumentException;
import mp.repository.GeneroRepository;

@Service
@AllArgsConstructor
public class GeneroServiceImpl implements GeneroService{
	private final GeneroRepository generoRepository;
	
	
	@Override
	public List<Genero> buscarTodos() {	
		List<Genero> generos = generoRepository.findAll();
		generos.forEach(genero -> genero.reducirLibros());
		return generos;
	}
	
	@Override
	public Genero buscarPorId(int id) {
		Optional<Genero> result = generoRepository.findById(id); 
		
		if(result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el genero con el id: " + id);
		}
		result.get().reducirLibros();
		return result.get();
	}

	@Override
	public Genero buscarPorNombre(String nombre) {
		Optional<Genero> result = generoRepository.findByNombre(nombre); 
		
		if(result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el genero con el nombre: " + nombre);
		}
		result.get().reducirLibros();
		return result.get();
	}

	//Crear o actualizar generos no puede crear libros, ni crear relaciones entre libro-genero
	@Override
	public void crear(Genero genero) {
		if(generoRepository.existsByNombre(genero.getNombre())) {
			throw new InvalidArgumentException("Ya existe un genero con ese nombre.");
		}
		generoRepository.save(genero);
		
	}
	
	@Override
	public boolean existGenero(String nombre) {
		return generoRepository.existsByNombre(nombre);
	}

	@Override
	public void actualizar(Genero genero) {
		if(!generoRepository.existsById(genero.getId())) {
			throw new EntityNotFoundException("El genero a actualizar no se ha encontrado en los registros.");
		}
		genero.setLibros(generoRepository.findById(genero.getId()).get().getLibros());
		generoRepository.save(genero);		
	}

	@Override
	public void eliminar(int id) {
		if(!generoRepository.existsById(id)) {
			throw new EntityNotFoundException("El genero a eliminar no se ha encontrado en los registros.");
		}
		generoRepository.deleteById(id);
	}	

}
