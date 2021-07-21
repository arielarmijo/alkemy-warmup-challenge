package tk.monkeycode.warmup.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.warmup.domain.dto.PostRequest;
import tk.monkeycode.warmup.domain.entity.Categoria;
import tk.monkeycode.warmup.domain.entity.Post;
import tk.monkeycode.warmup.domain.entity.Usuario;
import tk.monkeycode.warmup.exception.CategoriaNotFoundException;
import tk.monkeycode.warmup.exception.PostNotFoundException;
import tk.monkeycode.warmup.exception.WarmUpException;
import tk.monkeycode.warmup.repository.CategoriaRepository;
import tk.monkeycode.warmup.repository.PostRepository;
import tk.monkeycode.warmup.util.URLChecker;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final CategoriaRepository categoriaRepository;
	private final AuthService authService;
	
	@Transactional(readOnly = true)
	public List<Post> buscar(String titulo, String categoria) {
		// Crea criterio de oredenacion
		Sort sortBy = Sort.by(Sort.Direction.DESC, "fechaCreacion");
		// Inicializa los valores iniciales del filtro
		List<Post> results = postRepository.findByEnabled(true);
		List<Long> filteredPostsIds = results.stream().map(p -> p.getId()).collect(Collectors.toList());
		// Filtra post port titulo y nombre categoria
		if (titulo != null && !titulo.isBlank()) {
			results = postRepository.findByTituloContainingAndIdIn(titulo, filteredPostsIds, sortBy);
			filteredPostsIds = results.stream().map(p -> p.getId()).collect(Collectors.toList());
		}
		if (categoria != null && !categoria.isBlank()) {
			results = postRepository.findByCategoriaNombreContainingAndIdIn(categoria, filteredPostsIds, sortBy);
		}
	    return results;
	}
	
	@Transactional(readOnly = true)
	public Post buscar(Long id) {
		//return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		return postRepository.findByIdAndEnabled(id, true).orElseThrow(PostNotFoundException::new);
	}

	@Transactional
	public Post guardar(PostRequest postRequest) {
		// Verifica que la imagen exista
		if (!URLChecker.checkLink(postRequest.getImagen())) {
			throw new WarmUpException("Imagen no encontrada");
		}
		String nombreCategoria = postRequest.getCategoria();
		Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(nombreCategoria);
		Categoria categoria;
		if (categoriaOptional.isEmpty()) {
			categoria = categoriaRepository.save(new Categoria(null, nombreCategoria));
		} else {
			categoria = categoriaOptional.get();
		}
		Usuario usuario = authService.getCurrentUser();
		log.info("Usuario: {}", usuario.getUsername());
		Date today = new Date(System.currentTimeMillis());
		Post post = Post.builder()
						.titulo(postRequest.getTitulo())
						.contenido(postRequest.getContenido())
						.imagen(postRequest.getImagen())
						.fechaCreacion(today)
						.categoria(categoria)
						.autor(usuario)
						.enabled(true)
						.build();
		return postRepository.save(post);
	}

	@Transactional
	public Post actualizar(Long id, PostRequest postRequest) {
		Date today = new Date(System.currentTimeMillis());
		Categoria categoria = categoriaRepository.findByNombre(postRequest.getCategoria()).orElseThrow(CategoriaNotFoundException::new);
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		post.setTitulo(postRequest.getTitulo());
		post.setContenido(postRequest.getContenido());
		post.setImagen(postRequest.getImagen());
		post.setCategoria(categoria);
		post.setFechaModificacion(today);
		return postRepository.save(post);
	}
	
	@Transactional
	public void borrar(Long id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		postRepository.delete(post);
	}
	
	@Transactional
	public void borradoLogico(Long id) {
		Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		post.setEnabled(false);
		postRepository.save(post);
	}
	
}
