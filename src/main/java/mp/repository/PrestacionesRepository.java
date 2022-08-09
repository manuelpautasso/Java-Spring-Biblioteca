package mp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mp.domain.Prestaciones;

@Repository
public interface PrestacionesRepository extends JpaRepository<Prestaciones, Integer>{
	List<Prestaciones> findAllByLibroId(int id);
	List<Prestaciones> findAllByUsuarioId(int id);
	
	
	@Query("SELECT p FROM Prestaciones p WHERE p.devuelto IS NULL")
	List<Prestaciones> findAllActives();
	
	@Query("SELECT p FROM Prestaciones p WHERE p.usuario.id = ?1 AND p.devuelto IS NULL")
	List<Prestaciones> findAllActivesByUsuarioId(int usuarioId);
	
	@Query("SELECT p FROM Prestaciones p WHERE p.libro.id = ?1 AND p.devuelto IS NULL")
	List<Prestaciones> findAllActivesByLibroId(int libroId);
	
	@Query("SELECT p FROM Prestaciones p WHERE p.libro.id = ?1 AND p.usuario.id = ?2 AND p.devuelto IS NULL")
	Optional<Prestaciones> findActiveByLibroIdAndUsuarioId(int libroId, int usuarioId);
	
	@Query("SELECT COUNT(p.id) FROM Prestaciones p WHERE p.libro.id = ?1 AND p.usuario.id = ?2 AND p.devuelto IS NULL")
	int existActivePrestacion(int libroId, int usuarioId);
}
