package mp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mp.domain.Libro;
import mp.domain.Prestaciones;
import mp.domain.Usuario;
import mp.dto.PrestacionesResponse;
import mp.exception.EntityNotFoundException;
import mp.exception.InvalidPetitionException;
import mp.repository.PrestacionesRepository;
import static mp.dto.DtoUtil.fromListPrestacionesToResponse;

@Service
public class PrestacionesServiceImpl implements PrestacionesService{
	@Autowired
	private PrestacionesRepository prestacionesRepo;
	
	@Override
	public List<PrestacionesResponse> listarTodasPrestaciones() {		
		List<Prestaciones> result = prestacionesRepo.findAll();		
		return fromListPrestacionesToResponse(result);
	}
	
	@Override
	public List<PrestacionesResponse> listarTodasPrestacionesActivas() {		
		List<Prestaciones> result = prestacionesRepo.findAllActives();		
		return fromListPrestacionesToResponse(result);
	}

	@Override
	public List<PrestacionesResponse> historialPrestacionesPorUsuarioId(int usuarioId) {
		List<Prestaciones> result = prestacionesRepo.findAllByUsuarioId(usuarioId);		
		return fromListPrestacionesToResponse(result);
	}

	@Override
	public List<PrestacionesResponse> historialPrestacionesPorLibroId(int libroId) {
		List<Prestaciones> result = prestacionesRepo.findAllByLibroId(libroId);		
		return fromListPrestacionesToResponse(result);
	}

	@Override
	public List<PrestacionesResponse> prestacionesActivasPorUsuarioId(int usuarioId) {
		List<Prestaciones> result = prestacionesRepo.findAllActivesByUsuarioId(usuarioId);		
		return fromListPrestacionesToResponse(result);
	}

	@Override
	public List<PrestacionesResponse> prestacionesActivasPorLibroId(int libroId) {
		List<Prestaciones> result = prestacionesRepo.findAllActivesByLibroId(libroId);		
		return fromListPrestacionesToResponse(result);
	}

	@Override
	public void prestarLibro(Libro libro, Usuario usuario) {
		Prestaciones prestamo = new Prestaciones(libro, usuario);
		
		prestacionesRepo.save(prestamo);
	}

	@Override
	public void devolucionLibroById(int id) {
		//Buscar Prestacion
		Optional<Prestaciones> prestacionOpt = prestacionesRepo.findById(id);
		if(prestacionOpt.isEmpty()) {
			throw new EntityNotFoundException("No se ha encontrado la prestacion con el id ingresado");
		}
		
		Prestaciones prestacion = prestacionOpt.get();
		if(prestacion.getDevuelto() != null) {
			throw new InvalidPetitionException("La peticion ya fue cancelada en la fecha: " + prestacion.getDevuelto());
		}
				
		//Agregar fecha/hora de devolucion		
		prestacion.setDevuelto();
		
		//Actualizamos BD
		prestacionesRepo.save(prestacion);
		
	}
	
	@Override
	public void devolucionLibroByLibroIdAndUsuarioId(int libroId, int usuarioId) {
		//Buscar Prestacion
		Optional<Prestaciones> prestacionOpt = prestacionesRepo.findActiveByLibroIdAndUsuarioId(libroId, usuarioId);
		if(prestacionOpt.isEmpty()) {
			throw new EntityNotFoundException("No se ha encontrado la prestacion activa con el id ingresado");
		}
		
		Prestaciones prestacion = prestacionOpt.get();
		if(prestacion.getDevuelto() != null) {
			throw new InvalidPetitionException("La peticion ya fue cancelada en la fecha: " + prestacion.getDevuelto());
		}
				
		//Agregar fecha/hora de devolucion		
		prestacion.setDevuelto();
		
		//Actualizamos BD
		prestacionesRepo.save(prestacion);
	}

	@Override
	public boolean hayPrestacionActiva(Libro libro, Usuario usuario) {		
		return 0 < prestacionesRepo.existActivePrestacion(libro.getId(), usuario.getId());
	}
	
	
	

}
