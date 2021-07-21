package tk.monkeycode.warmup.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.warmup.domain.dto.PostRequest;
import tk.monkeycode.warmup.domain.dto.PostResponse;
import tk.monkeycode.warmup.domain.entity.Post;
import tk.monkeycode.warmup.service.PostService;
import tk.monkeycode.warmup.util.PostMapper;

@Slf4j
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
//@Secured({"ROLE_USER"})
public class PostController {

	private final PostService postService;

	@GetMapping("")
	public List<PostResponse> mostrar(@RequestParam(name = "title", required = false) String titulo,
									  @RequestParam(name = "category", required = false) String categoria) {
		log.info("Buscando posts por title = {} y category = {}", titulo, categoria);
		return postService.buscar(titulo, categoria).stream().map(PostMapper::map).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> mostrar(@PathVariable Long id) {
		Post post = postService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(PostMapper.map(post));
	}
	
	@PostMapping("")
	public ResponseEntity<PostResponse> guardar(@Valid @RequestBody PostRequest postRequest) {
		Post post = postService.guardar(postRequest);
		return ResponseEntity.status(HttpStatus.OK).body(PostMapper.map(post));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<PostResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest) {
		Post post = postService.actualizar(id, postRequest);
		return ResponseEntity.status(HttpStatus.OK).body(PostMapper.map(post));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> borrar(@PathVariable Long id) {
		//postService.borrar(id);
		postService.borradoLogico(id);
		return ResponseEntity.status(HttpStatus.OK).body("Post borrado con éxito.");
	}

}
