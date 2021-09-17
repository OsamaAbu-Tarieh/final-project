package osama.atyponfinalproject.exception;

public class ConnectionFailedException extends Exception{

	private static final long serialVersionUID = 1L;

	public ConnectionFailedException(String message) {
		super(message);
	}
}
