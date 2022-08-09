package mp.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Usuario;

@Component @Slf4j
public class JwtTokenUtil {
	
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
	
	@Value("${app.jwt.secret}")
	private String SECRET_KEY;
	
	
	public String generateAccessToken(Usuario usuario) {
		log.info("Generando token para {}", usuario.getUsername());
		return Jwts.builder()
				.setSubject(String.format("%s,%s,%s", usuario.getId(), 
						usuario.getUsername(), usuario.getEmail()))
				.claim("roles", usuario.getRoles().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	public boolean validateAccessToken(String token) {		
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			log.error("JWT expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.error("Token is null, empty or only whitespace", ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			log.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			log.error("Signature validation failed");
		}
		
		return false;
	}
	
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
}
