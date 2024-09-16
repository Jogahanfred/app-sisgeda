package pe.mil.fap.exception;

public class BadRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L; 

    public BadRequestException(String detalle) {
    	 super(detalle);
    }
}