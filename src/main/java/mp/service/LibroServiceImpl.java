package mp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Genero;
import mp.domain.Libro;
import mp.exception.EntityNotFoundException;
import mp.exception.InvalidPetitionException;
import mp.repository.LibroRepository;

@Service
@AllArgsConstructor
@Slf4j
public class LibroServiceImpl implements LibroService {
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
		if (result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el libro con el id: " + id);
		}
		result.get().reducirGeneros();
		return result.get();
	}

	@Override
	public Libro buscarPorNombre(String nombre) {
		Optional<Libro> result = libroRepository.findByNombre(nombre);
		if (result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro el libro con el nombre: " + nombre);
		}
		result.get().reducirGeneros();
		return result.get();
	}
	
	@Override
	public List<Libro> buscarPorParteDeNombre(String nombre){
		List<Libro> result = libroRepository.findByNombreContainingIgnoreCase(nombre);
		if(result.isEmpty()) {
			throw new EntityNotFoundException("No se encontro ningun libro que tuviera en el nombre: " + nombre);
		}
		for(Libro libro: result) libro.reducirGeneros();
		return result;
	}

	@Override
	public void crear(Libro libro) {		
		log.info("Ahora vamos a crear el libro: " + libro.getNombre() + " sin generos asociados.");
		Set<Genero> generosSet = libro.getGeneros();
		libro.setGenerosANull();
		libro = libroRepository.save(libro);

		if (!generosSet.isEmpty()) {
			log.info("Asociamos los generos al libro y actualizamos en la BD.");
			libro.setGeneros(generosSet);
			libroRepository.save(libro);
		}
	}

	@Override
	public void actualizar(Libro libro) {
		if (!libroRepository.existsById(libro.getId())) {
			throw new EntityNotFoundException("El libro a actualizar no se ha encontrado en los registros.");
		}
		libroRepository.save(libro);		
	}

	@Override
	public void eliminar(int id) {
		if (!libroRepository.existsById(id)) {
			throw new EntityNotFoundException("El libro a eliminar no se ha encontrado en los registros.");
		}

		libroRepository.deleteById(id);
	}

	@Override
	public Boolean existeLibroPorId(int libroId) {		
		return libroRepository.existsById(libroId);
	}


	@Override
	public Boolean hayStockDisponible(int libroId) {		
		return 0 < libroRepository.stockDisponibleByLibroId(libroId);
	}

	@Override
	public void reducirEn1StockDisponible(int libroId) {
		int stockAnterior = libroRepository.stockDisponibleByLibroId(libroId);
		if(stockAnterior <= 0) {
			throw new InvalidPetitionException("No se puede reducir el stock de este libro");
		}
		
		libroRepository.actualizarStockDisponible(libroId, stockAnterior-1);
	}

	@Override
	public void aumentarEn1StockDisponible(int libroId) {
		int stockAnterior = libroRepository.stockDisponibleByLibroId(libroId);		
		
		libroRepository.actualizarStockDisponible(libroId, stockAnterior+1);		
	}

}
