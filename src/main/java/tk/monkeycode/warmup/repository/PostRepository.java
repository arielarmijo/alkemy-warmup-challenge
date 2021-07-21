package tk.monkeycode.warmup.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import tk.monkeycode.warmup.domain.entity.Categoria;
import tk.monkeycode.warmup.domain.entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

	List<Post> findAll();
	List<Post> findAll(Sort by);
	List<Post> findByEnabled(Boolean enabled);
	List<Post> findByEnabled(Boolean enabled, Sort by);
	Optional<Post> findById(Long id);
	Optional<Post> findByIdAndEnabled(Long id, Boolean enabled);
	List<Post> findByTituloContainingAndIdIn(String titulo, Collection<Long> postIds, Sort by);
	List<Post> findByCategoriaNombreContainingAndIdIn(String categoria, Collection<Long> posts, Sort by);
	
}
