package tk.monkeycode.warmup.domain.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class PostResponse {

	private Long id;
	private String titulo;
	private String imagen;
	private String categoria;
	private Date fechaCreacion;
	private Date fechaModificacion;
	
}
