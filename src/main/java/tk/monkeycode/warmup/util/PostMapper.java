package tk.monkeycode.warmup.util;

import tk.monkeycode.warmup.domain.dto.PostResponse;
import tk.monkeycode.warmup.domain.entity.Post;

public class PostMapper {
		
	public static PostResponse map(Post post) {
		return PostResponse.builder()
				.id(post.getId())
				.titulo(post.getTitulo())
				.imagen(post.getImagen())
				.categoria(post.getCategoria().getNombre())
				.fechaCreacion(post.getFechaCreacion())
				.fechaModificacion(post.getFechaModificacion())
				.build();
	}

}
