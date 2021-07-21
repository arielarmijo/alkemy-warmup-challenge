package tk.monkeycode.warmup.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.warmup.domain.entity.Rol;

public interface RolRepository extends CrudRepository<Rol, Long> {

	Optional<Rol> findByNombre(String nombre);
	
}
