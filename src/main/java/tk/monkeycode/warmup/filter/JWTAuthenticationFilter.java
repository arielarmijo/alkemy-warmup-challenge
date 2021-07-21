package tk.monkeycode.warmup.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.warmup.service.JWTService;

@Slf4j
@Component
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JWTService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		
		// Validación del header
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("Header: {}", header);
		
		if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Validación del token
		String token = header.split(" ")[1].trim();
		
		if (!jwtService.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Obtiene la identidad del usuario y configura el spring security context
		String username = jwtService.getUsername(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Continua con el resto de los filtros
		filterChain.doFilter(request, response);
		
	}

}
