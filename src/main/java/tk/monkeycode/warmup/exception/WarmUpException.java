package tk.monkeycode.warmup.exception;

public class WarmUpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public WarmUpException() {
		super();
	}
	
	public WarmUpException(String message) {
		super(message);
	}
	
	public WarmUpException(String message, Throwable e) {
		super(message, e);
	}

}
