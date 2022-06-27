package mp.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mp.domain.Genero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroRequest {
	private int id;
	private String nombre;
	private String descripcion;
	private String portada;
	private int cantidadDisponible;
	private Set<GeneroRequest> generos;
	
	
	//Devuelve un Set de Generos sin libros asociados
	public Set<Genero> getSetGeneros() {
		Set<Genero> generosEntity = new HashSet<>();
		for(GeneroRequest generoReq: generos) {
			Genero genero = new Genero();
			genero.setId(generoReq.getId());
			genero.setNombre(generoReq.getNombre());
			generosEntity.add(genero);
		}
		
		return generosEntity;
	};
}
