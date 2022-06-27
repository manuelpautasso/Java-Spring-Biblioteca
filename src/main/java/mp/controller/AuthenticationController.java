package mp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Usuario;
import mp.dto.AuthRequest;
import mp.dto.AuthResponse;
import mp.dto.UsuarioRequest;
import mp.security.jwt.JwtTokenUtil;
import mp.service.MailService;
import mp.service.UsuarioService;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
	private UsuarioService usuarioService;
	private MailService mailService;
	@Autowired AuthenticationManager authManager;
	@Autowired JwtTokenUtil jwtUtil;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) throws IOException {
		log.info("Creando el usuario: " + usuarioRequest.getUsername());
		usuarioService.crearUsuario(fromUsuarioRequestToEntity(usuarioRequest));
		
		mailService.sendConfirmationEmail(usuarioRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> loguearUsuario(@RequestBody AuthRequest usuarioRequest) {
		try {
			log.info("Logueando el usuario: " + usuarioRequest.getUsername());
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							usuarioRequest.getUsername(), usuarioRequest.getPassword())
			);
			Usuario usuario = (Usuario) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(usuario);
			AuthResponse response = new AuthResponse(usuario.getUsername(), accessToken);
			
			return ResponseEntity.ok().body(response);
			
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	
	
	private Usuario fromUsuarioRequestToEntity(UsuarioRequest usuarioRequest) {
		Usuario entity = new Usuario();
		entity.setUsername(usuarioRequest.getUsername().trim().toLowerCase());
		entity.setEmail(usuarioRequest.getEmail().trim().toLowerCase());
		entity.setPassword(usuarioRequest.getPassword());
		
		return entity;
	}
}
