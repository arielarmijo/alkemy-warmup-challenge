package tk.monkeycode.warmup.exception;

public class PostNotFoundException extends WarmUpException {
	
	private static final long serialVersionUID = 1L;
	
	public PostNotFoundException() {
		super("Post no encontrado.");
	}

}
