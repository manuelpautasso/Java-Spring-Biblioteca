package mp.dto;

import java.util.Set;

import lombok.Data;
import mp.domain.Genero;
import mp.domain.Prestaciones;

@Data
public class LibroResponse {
	private int id;
	private String nombre;
	private String descripcion;
	private String portada;
	private int cantidadInventario;
	private int cantidadDisponible;
	private Set<Genero> generos;
	private Set<Prestaciones> prestaciones;
}
