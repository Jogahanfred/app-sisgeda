package pe.mil.fap.service.administration.usp.impl; 
  
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.AeronaveEntity;
import pe.mil.fap.entity.administration.EstandarEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.mappers.administration.inf.EstandarMapper; 
import pe.mil.fap.model.administration.EstandarDTO;
import pe.mil.fap.model.bussiness.MisionDTO;
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

	@Override
	public Optional<EstandarDTO> buscarPorCodigo(String coCodigo) throws ServiceException {
		try { 
			Optional<EstandarEntity> optEntity = estandarUSPRepository.buscarPorCodigo(coCodigo); 
			if(!optEntity.isPresent()){ 
				throw new ServiceException("Estándar no existe en nuestra base de datos");
			}
			EstandarDTO dto = estandarMapper.toDTO(optEntity.get());
			return Optional.of(dto);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	} 
	 
}
