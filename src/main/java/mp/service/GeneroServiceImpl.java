package mp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mp.domain.Genero;
import mp.exception.EntityNotFoundException;
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

	@Override
	public void crear(Genero genero) {
		generoRepository.save(genero);
		
	}

	@Override
	public void actualizar(Genero genero) {
		if(!generoRepository.existsById(genero.getId())) {
			throw new EntityNotFoundException("El genero a actualizar no se ha encontrado en los registros.");
		}
		generoRepository.save(genero);		
	}

	@Override
	public void eliminar(int id) {
		generoRepository.deleteById(id);
	}	

}
