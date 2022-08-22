package mp.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mp.domain.Rol;
import mp.domain.Usuario;
import mp.exception.InvalidArgumentException;
import mp.exception.InvalidPetitionException;
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
	public Optional<Usuario> buscarUsuarioPorUsername(String username) {		
		return usuarioRepository.findByUsername(username);
	}
	
	@Override
	public int buscarIdUsuarioPorUsername(String username) {
		Optional<Usuario> usuarioOpt = buscarUsuarioPorUsername(username);
		return usuarioOpt.isPresent() ? usuarioOpt.get().getId() : 0;
	}

	@Override
	public void agregarRolAUsuario(String username, String rolNombre) {
		Optional<Usuario> usuarioBD = usuarioRepository.findByUsername(username);
		if(usuarioBD.isEmpty()) {
			throw new EntityNotFoundException("No se encontro ningún usuario con el username " + username);
		}
		Usuario usuario = usuarioBD.get();
				
		Rol rolBD = rolRepository.findByNombre(rolNombre);
		
		if(usuario.getRoles().contains(rolBD)) {
			throw new InvalidPetitionException("El usuario ya tiene el rol que se le quiere asignar.");
		}
		usuario.agregarRol(rolBD);
		
		usuarioRepository.save(usuario);
	}
	
	
	//Busca si existe un usuario en la BD por username o email
	private boolean existeUsuario(Usuario usuario) {
		boolean existe = false;
		if(usuarioRepository.findByUsername(usuario.getUsername()).isPresent() ||
				usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			existe = true;
		}		
		return existe;
	}

	

}
