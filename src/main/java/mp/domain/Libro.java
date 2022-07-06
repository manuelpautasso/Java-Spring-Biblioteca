package mp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Libro implements Serializable{

	private static final long serialVersionUID = 1L;

	public Libro(int id, String nombre, String descripcion, String portada, int cantidadDisponible) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.portada = portada;
		this.cantidadDisponible = cantidadDisponible;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String nombre;
	
	private String descripcion;
	
	private String portada;
	
	@Column(name = "cantidad_disponible")
	private int cantidadDisponible;
	
	//@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
	  name = "libro_genero", 
	  joinColumns = @JoinColumn(name = "libro_id"), 
	  inverseJoinColumns = @JoinColumn(name = "genero_id"))
	private Set<Genero> generos = new HashSet<>();
		
	
	public void agregarGenero(Genero genero) {
		generos.add(genero);
	}
	
	public void eliminarGenero(Genero genero) {
		generos.remove(genero);
	}
	
	public void reducirGeneros() {
		generos.forEach(genero -> genero.setLibrosANull());
	}
	
	public void setGenerosANull() {
		generos = null;
	}
	
	
	
	/* Vinculos con generos y prestaciones (disponibilidad)
	 * 		private int disponibilidad;
	 * 		private String generos;
	*/
}
