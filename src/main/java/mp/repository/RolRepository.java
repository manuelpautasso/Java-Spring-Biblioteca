package mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mp.domain.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>{
	public Rol findByNombre(String nombre);
}
