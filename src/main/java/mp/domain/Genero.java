package mp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@NoArgsConstructor
public class Genero implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String nombre;
	
	@ManyToMany(mappedBy = "generos")
	private Set<Libro> libros = new HashSet<>();
	
	public Genero(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genero other = (Genero) obj;
		return id == other.id && Objects.equals(nombre, other.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}
	
	public void agregarLibro(Libro libro) {
		libros.add(libro);
	}
	
	public void eliminarLibro(Libro libro) {
		libros.remove(libro);
	}
	
	public void setLibrosANull() {
		libros = null;
	}
	
	public void reducirLibros() {
		libros.forEach(libro -> libro.setGenerosANull());		
	}
}
