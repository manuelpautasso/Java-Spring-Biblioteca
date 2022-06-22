package mp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mp.domain.Libro;
import mp.exception.EntityNotFoundException;
import mp.repository.LibroRepository;

@Service
@AllArgsConstructor
public class LibroServiceImpl implements LibroService{
	private LibroRepository libroRepository;
	
	@Override
	public List<Libro> buscarTodos() {	
		List<Libro> result = libroRepository.findAll();
		result.forEach(libro -> libro.reducirGeneros());
		return result;
	}
	
	@Override
	public Libro buscarPorId(int id) {
		Optional<Libro> result = libroRepository.findById(id);
		if(result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el libro con el id: " + id);
		}
		result.get().reducirGeneros();
		return result.get();
	}

	@Override
	public Libro buscarPorNombre(String nombre) {
		Optional<Libro> result = libroRepository.findByNombre(nombre);
		if(result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el libro con el nombre: " + nombre);
		}
		result.get().reducirGeneros();
		return result.get();
	}

	@Override
	public void crear(Libro libro) {
		libroRepository.save(libro);		
	}

	@Override
	public void actualizar(Libro libro) {
		if(!libroRepository.existsById(libro.getId())) {
			throw new EntityNotFoundException("El libro a actualizar no se ha encontrado en los registros.");
		}
		libroRepository.save(libro);	
	}

	@Override
	public void eliminar(int id) {
		if(!libroRepository.existsById(id)) {
			throw new EntityNotFoundException("El libro a eliminar no se ha encontrado en los registros.");
		}
		libroRepository.deleteById(id);
	}

}
