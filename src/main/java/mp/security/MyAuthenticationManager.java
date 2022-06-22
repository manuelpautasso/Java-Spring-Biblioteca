package mp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import mp.domain.Usuario;
import mp.exception.InvalidArgumentException;
import mp.service.UsuarioService;

@Slf4j
public class MyAuthenticationManager implements AuthenticationManager {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Buscamos el usuario en MyAuthManager");
		Optional<Usuario> usuario = usuarioService.buscarUsuarioPorUsername(authentication.getName());
		if (usuario.isEmpty()) {
			throw new InvalidArgumentException("El usuario no fue encontrado");
		}		
		
		if (!passwordEncoder.matches(authentication.getCredentials().toString(), usuario.get().getPassword())) {
			throw new InvalidArgumentException("El usuario o la contraseña es inválida");
		}
		
		Authentication authResponse = new UsernamePasswordAuthenticationToken(usuario.get(), null, usuario.get().getAuthorities());

		return authResponse;
	}
	
}
