package tk.monkeycode.warmup.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.warmup.domain.dto.AuthenticationResponse;
import tk.monkeycode.warmup.domain.dto.LoginRequest;
import tk.monkeycode.warmup.domain.dto.RegisterRequest;
import tk.monkeycode.warmup.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/sign_up")
	public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest) {
		log.info("Registrando usuario: {}", registerRequest);
		authService.signup(registerRequest);
		return ResponseEntity.ok().body("Usuario registrado exitosamente.");
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("Logeando usuario: {}", loginRequest);
		return authService.login(loginRequest);
	}
	

}
