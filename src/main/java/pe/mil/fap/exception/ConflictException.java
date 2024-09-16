package pe.mil.fap.exception;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String detalle) {
		super(detalle);
	}
}
