package mp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mp.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByUsername(String username);
	Optional<Usuario> findByEmail(String email);
}
