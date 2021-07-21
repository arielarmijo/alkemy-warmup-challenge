package tk.monkeycode.warmup.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class RegisterRequest {
	
	@NotBlank(message = "Email es obliglatorio.")
	@Email(message = "Ingrese un correo válido.")
	private String email;
	
	@NotBlank(message = "Password es obliglatorio.")
	private String password;
	
	@NotBlank(message = "Password es obliglatorio.")
	private String rePassword;

}
