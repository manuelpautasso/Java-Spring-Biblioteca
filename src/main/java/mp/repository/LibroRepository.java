package mp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mp.domain.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer>{
	Optional<Libro> findByNombre(String nombre);	
	List<Libro> findByNombreContainingIgnoreCase(String nombre);
	
	@Query("SELECT l.cantidadDisponible FROM Libro l WHERE l.id = ?1")
	int stockDisponibleByLibroId(int libroId);
	
	@Query("UPDATE Libro l SET l.cantidadDisponible = ?2 WHERE l.id = ?1")
	void actualizarStockDisponible(int libroId, int nuevoStock);
}
