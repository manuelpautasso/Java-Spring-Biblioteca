package mp.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroRequest {
	private int id;
	private String nombre;
	private Set<LibroRequest> libros;
	
	public void setLibrosASinGeneros() {
		for(LibroRequest libro: libros) {
			libro.setGeneros(null);
		}
	};
}
