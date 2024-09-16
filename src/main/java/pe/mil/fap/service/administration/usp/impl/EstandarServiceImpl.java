package pe.mil.fap.service.administration.usp.impl; 
  
import java.util.List; 

import org.springframework.stereotype.Service; 
import pe.mil.fap.entity.administration.EstandarEntity; 
import pe.mil.fap.mappers.administration.inf.EstandarMapper; 
import pe.mil.fap.model.administration.EstandarDTO; 
import pe.mil.fap.repository.administration.usp.inf.EstandarUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.EstandarService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class EstandarServiceImpl implements EstandarService {

	private final EstandarMapper estandarMapper;
	private final EstandarUSPRepository estandarUSPRepository;

	public EstandarServiceImpl(final EstandarUSPRepository estandarUSPRepository, final EstandarMapper estandarMapper) {
		super();
		this.estandarMapper = estandarMapper;
		this.estandarUSPRepository = estandarUSPRepository;
	}

	@Override
	public List<EstandarDTO> listarEstandares() throws ServiceException {
		try {
			List<EstandarEntity> lstEntity = estandarUSPRepository.listarEstandares(); 
		    List<EstandarDTO> lstDTO = estandarMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} 
	 
}
