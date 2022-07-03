package mp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mp.domain.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer>{
	Optional<Genero> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
}
