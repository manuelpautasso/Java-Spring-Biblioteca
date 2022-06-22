package mp.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import mp.domain.Rol;
import mp.domain.Usuario;

@Component @Slf4j
public class JwtTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = getAccessToken(request);
		
		if(!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
		
	}



	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
	
	
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}
	
	


	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(token);
		
		UsernamePasswordAuthenticationToken 
			authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
	}

	
	private UserDetails getUserDetails(String token) {
		Usuario userDetails = new Usuario();
		
		Claims claims = jwtUtil.parseClaims(token);
		String subject = (String) claims.get(Claims.SUBJECT);
		String roles = (String) claims.get("roles");
		
		log.info("Subject: " + subject);
		log.info("roles: " + roles);
		
		roles = roles.replace("[", "").replace("]", "");
		String[] roleNames = roles.split(",");
		
		for (String aRoleName : roleNames) {
			userDetails.agregarRol(new Rol(aRoleName));
		}
		
		String[] jwtSubject = subject.split(",");
		
		userDetails.setId(Integer.parseInt(jwtSubject[0]));
		userDetails.setUsername(jwtSubject[1]);
		userDetails.setEmail(jwtSubject[2]);
		
		return userDetails;
	}
	
	
	
	
}
