package tk.monkeycode.warmup.exception;

public class UsuarioNotFoundException extends WarmUpException {

private static final long serialVersionUID = 1L;
	
	public UsuarioNotFoundException() {
		super("Usuario no encontrado.");
	}
	
}
