package mp.dto;

import mp.domain.Genero;
import mp.domain.Libro;
import mp.exception.InvalidArgumentException;

public class DtoUtil {
	
	public static Libro fromLibroRequestToEntity(LibroRequest libroRequest) throws InvalidArgumentException {
		if (libroRequest.getNombre() == null || libroRequest.getDescripcion() == null) {
			throw new InvalidArgumentException("Argumentos invalidos o nulos para un libro.");
		}

		Libro libroEntity = new Libro(libroRequest.getId(), libroRequest.getNombre(), libroRequest.getDescripcion(),
				libroRequest.getPortada(), libroRequest.getCantidadDisponible());
		if (libroRequest.getGeneros() != null) {
			libroEntity.setGeneros(libroRequest.getSetGeneros());
			/*for(GeneroRequest generoReq : libroRequest.getGeneros()) {
				libroEntity.agregarGenero(fromGeneroRequestToEntity(generoReq));
			}*/
		}
		return libroEntity;
	}
	
	
	
	
	public static Genero fromGeneroRequestToEntity(GeneroRequest generoRequest) {
		if(generoRequest.getNombre().isBlank()) {
			throw new InvalidArgumentException("Argumentos invalidos o nulos para un genero.");
		}
		return new Genero(generoRequest.getId(), generoRequest.getNombre());
	}
}
