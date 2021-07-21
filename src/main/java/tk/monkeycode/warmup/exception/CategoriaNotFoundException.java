package tk.monkeycode.warmup.exception;

public class CategoriaNotFoundException extends WarmUpException {

	private static final long serialVersionUID = 1L;
	
	public CategoriaNotFoundException() {
		super("Categoría no encontrada.");
	}

}
