package pe.mil.fap.exception;
  
import static pe.mil.fap.common.enums.SeveridadEnum.*; 
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException; 
import org.springframework.http.converter.HttpMessageNotReadableException; 
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.exception.ServiceException;

@ControllerAdvice
@Order(2)
public class ControllerExceptionHandler {
	
	@ExceptionHandler({ ConflictException.class })
	@ResponseBody
	public MessageDTO conflict(Exception exception) {
		return MessageDTO.builder()
				 .type(WARNING.getValor())
				 .message(exception.getMessage()) 
				 .build();  
	}
	
	@ExceptionHandler({ BadRequestException.class, 
						DuplicateKeyException.class, 
						WebExchangeBindException.class,	
						HttpMessageNotReadableException.class,  
						ServerWebInputException.class})
	@ResponseBody
	public MessageDTO badRequest(Exception exception) { 
		return MessageDTO.builder()
				 .type(WARNING.getValor())
				 .message(exception.getMessage()) 
				 .build(); 
	}
	
	@ExceptionHandler({ RepositoryException.class, 
					    ServiceException.class, 
					    Exception.class })
	@ResponseBody
	public MessageDTO exception(Exception exception) {    
		exception.printStackTrace();
		return MessageDTO.builder()
				 .type(ERROR.getValor())
				 .message(exception.getMessage())
				 .build();  
	}  
}
