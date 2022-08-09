package mp.dto;

import java.util.LinkedList;
import java.util.List;

import mp.domain.Genero;
import mp.domain.Libro;
import mp.domain.Prestaciones;
import mp.exception.InvalidArgumentException;


public class DtoUtil {
	
	public static Libro fromLibroRequestToEntity(LibroRequest libroRequest) throws InvalidArgumentException {
		if (libroRequest.getNombre() == null || libroRequest.getDescripcion() == null) {
			throw new InvalidArgumentException("Argumentos invalidos o nulos para un libro.");
		}

		Libro libroEntity = new Libro(libroRequest.getId(), libroRequest.getNombre(), libroRequest.getDescripcion(),
				libroRequest.getPortada(), libroRequest.getCantidadInventario());
		if (libroRequest.getGeneros() != null) {
			libroEntity.setGeneros(libroRequest.getSetGeneros());			
		}
		return libroEntity;
	}
	
	
	
	
	public static Genero fromGeneroRequestToEntity(GeneroRequest generoRequest) {
		if(generoRequest.getNombre().isBlank()) {
			throw new InvalidArgumentException("Argumentos invalidos o nulos para un genero.");
		}
		return new Genero(generoRequest.getId(), generoRequest.getNombre());
	}
	
	
	public static List<PrestacionesResponse> fromListPrestacionesToResponse(List<Prestaciones> prestaciones){
		List<PrestacionesResponse> prestacionesResponse = new LinkedList<>();
		for(Prestaciones prestacion : prestaciones) {
			PrestacionesResponse prestDTO = fromPrestacionesToResponse(prestacion);
			prestacionesResponse.add(prestDTO);
		}
		
		return prestacionesResponse;
	}
	
	public static PrestacionesResponse fromPrestacionesToResponse(Prestaciones prestaciones){	
		PrestacionesResponse prestDTO = new PrestacionesResponse();
		
		prestDTO.setId(prestaciones.getId());
		prestDTO.setEgreso(prestaciones.getEgreso());
		prestDTO.setVencimiento(prestaciones.getVencimiento());
		prestDTO.setDevuelto(prestaciones.getDevuelto());
		
		
		UsuarioResponse userResponse = new UsuarioResponse();
		userResponse.setUsername(prestaciones.getUsuario().getUsername());
		userResponse.setEmail(prestaciones.getUsuario().getEmail());
		prestDTO.setUsuario(userResponse);
		
		
		LibroResponse libroResponse = fromLibroEntityToResponse(prestaciones.getLibro());				
		prestDTO.setLibro(libroResponse);
		
		
		return prestDTO;
	}
	
	public static LibroResponse fromLibroEntityToResponse(Libro libro){
		LibroResponse libroResponse = new LibroResponse();
		
		libroResponse.setId(libro.getId());
		libroResponse.setNombre(libro.getNombre());		
				
		return libroResponse;
	}
}
