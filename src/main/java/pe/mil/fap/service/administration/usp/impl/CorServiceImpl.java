package pe.mil.fap.service.administration.usp.impl;
 
import org.springframework.stereotype.Service; 
import pe.mil.fap.mappers.administration.inf.CorMapper; 
import pe.mil.fap.model.administration.CorDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.CorUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.CorService; 
import pe.mil.fap.service.exception.ServiceException;

@Service
public class CorServiceImpl implements CorService{

	private final CorUSPRepository corUSPRepository;
	private final CorMapper corMapper;
	
	public CorServiceImpl(final CorUSPRepository corUSPRepository,
						  final CorMapper corMapper) {
		super();
		this.corUSPRepository = corUSPRepository;
		this.corMapper = corMapper;
	}

	@Override
	public MessageDTO guardar(CorDTO dto) throws ServiceException {
		try { 
			String mensaje = corUSPRepository.guardar(corMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
