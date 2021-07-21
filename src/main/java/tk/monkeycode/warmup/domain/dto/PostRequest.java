package tk.monkeycode.warmup.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PostRequest {
	
	@NotBlank(message = "Título es obliglatorio.")
	private String titulo;
	
	private String contenido;
	
	@Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)", message = "url imagen inválida.")
	private String imagen;
	
	@NotBlank(message = "Nombre categoría es obliglatorio.")
	private String categoria;

}
