package tk.monkeycode.warmup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tk.monkeycode.warmup.domain.dto.AuthenticationResponse;
import tk.monkeycode.warmup.domain.dto.LoginRequest;
import tk.monkeycode.warmup.domain.dto.RegisterRequest;
import tk.monkeycode.warmup.domain.entity.Rol;
import tk.monkeycode.warmup.domain.entity.Usuario;
import tk.monkeycode.warmup.exception.UsuarioNotFoundException;
import tk.monkeycode.warmup.exception.WarmUpException;
import tk.monkeycode.warmup.repository.RolRepository;
import tk.monkeycode.warmup.repository.UsuarioRepository;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final UsuarioRepository usuarioRepository;
	private final RolRepository rolRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;

	@Transactional
	public Usuario signup(RegisterRequest registerRequest) {
		
		// Comprueba si el usuario exite
		if (usuarioRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
			throw new WarmUpException("Nombre de usuario ya existe.");
		}
		
		// Comprueba que los password coincidan
		if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
			throw  new WarmUpException("Las contraseñas no coinciden.");
		}
		
		// Crea usuario con rol por defecto
		Optional<Rol> rol = rolRepository.findByNombre("ROLE_USER");
		List<Rol> roles = new ArrayList<>();
		
		if (rol.isPresent()) {
			roles.add(rol.get());
		} else {
			roles.add(new Rol(null, "ROLE_USER"));
		}

		var nvoUsuario = new Usuario();
		nvoUsuario.setEmail(registerRequest.getEmail());
		nvoUsuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		nvoUsuario.setEnabled(true);
		nvoUsuario.setRoles(roles);

		return usuarioRepository.save(nvoUsuario);
		
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
    	var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        Usuario principal = (Usuario) authenticate.getPrincipal();
        String authenticationToken = jwtService.generate(principal.getUsername());
        return AuthenticationResponse.builder().authenticationToken(authenticationToken).build();
    }
	
	@Transactional(readOnly = true)
    public Usuario getCurrentUser() {
        var principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByEmail(principal.getUsername()).orElseThrow(UsuarioNotFoundException::new);
    }
	
}
