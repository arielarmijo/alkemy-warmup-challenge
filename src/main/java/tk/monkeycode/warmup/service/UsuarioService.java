package tk.monkeycode.warmup.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tk.monkeycode.warmup.repository.UsuarioRepository;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario %s no encontrado.", username)));
	}

}
