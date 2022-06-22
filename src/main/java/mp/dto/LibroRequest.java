package mp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroRequest {
	public int id;
	public String nombre;
	public String descripcion;
	public String portada;
	public int cantidadDisponible;
}
