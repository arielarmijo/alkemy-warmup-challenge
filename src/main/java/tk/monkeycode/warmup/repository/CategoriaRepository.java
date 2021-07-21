package tk.monkeycode.warmup.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.warmup.domain.entity.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

	Optional<Categoria> findByNombre(String nombre);
}
