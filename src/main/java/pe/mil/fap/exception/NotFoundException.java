package pe.mil.fap.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String detalle) {
		super(detalle);
	}
}
