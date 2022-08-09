package mp.service;

import java.util.List;
import java.util.Optional;

import mp.domain.Usuario;

public interface UsuarioService {
	List<Usuario> listarUsuarios();
	Usuario crearUsuario(Usuario usuario);
	Optional<Usuario> buscarUsuarioPorUsername(String nombre);
	int buscarIdUsuarioPorUsername(String nombre);
	void agregarRolAUsuario(String username, String rolNombre);
}
