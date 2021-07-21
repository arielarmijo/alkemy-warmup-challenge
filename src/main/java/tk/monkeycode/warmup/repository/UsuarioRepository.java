package tk.monkeycode.warmup.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.warmup.domain.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>  {
	
	Optional<Usuario> findByEmail(String username);

}
