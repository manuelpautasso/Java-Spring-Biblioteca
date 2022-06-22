package mp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mp.domain.Rol;
import mp.domain.Usuario;
import mp.exception.InvalidArgumentException;
import mp.repository.RolRepository;
import mp.repository.UsuarioRepository;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	private final UsuarioRepository usuarioRepository;
	
	private final RolRepository rolRepository;

	@Override
	public List<Usuario> listarUsuarios() {
		
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		if(existeUsuario(usuario)){
			throw new InvalidArgumentException("El email o username ya pertenece a otro usuario.");
		}
		
		String oldPassword = usuario.getPassword();
		String encodedPassword = passwordEncoder.encode(oldPassword);
		
		usuario.setPassword(encodedPassword);		
		
		usuario.agregarRol(rolRepository.findByNombre("ROLE_USER"));
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> buscarUsuarioPorUsername(String nombre) {		
		return usuarioRepository.findByUsername(nombre);
	}

	@Override
	public void agregarRolAUsuario(String username, String rolNombre) {
		// TODO Auto-generated method stub
		
	}
	
	
	private boolean existeUsuario(Usuario usuario) {
		boolean existe = false;
		if(usuarioRepository.findByUsername(usuario.getUsername()).isPresent() ||
				usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			existe = true;
		}		
		return existe;
	}

}
