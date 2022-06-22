package mp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mp.domain.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer>{
	Optional<Libro> findByNombre(String nombre);	
}
