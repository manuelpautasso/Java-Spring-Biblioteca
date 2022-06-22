package mp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mp.domain.Prestaciones;

public interface PrestacionesRepository extends JpaRepository<Prestaciones, Integer>{
	Optional<Prestaciones> findByLibroId(int id);
	Optional<Prestaciones> findByUsuarioId(int id);
}
