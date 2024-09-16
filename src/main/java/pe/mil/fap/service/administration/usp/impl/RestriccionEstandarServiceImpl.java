package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;
 
import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.mappers.administration.inf.RestriccionEstandarMapper; 
import pe.mil.fap.model.administration.RestriccionEstandarDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.RestriccionEstandarUSPRepository;
import pe.mil.fap.service.administration.usp.inf.RestriccionEstandarService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class RestriccionEstandarServiceImpl implements RestriccionEstandarService{

	private final RestriccionEstandarUSPRepository restriccionEstandarUSPRepository;
	private final RestriccionEstandarMapper restriccionEstandarMapper;
	
	public RestriccionEstandarServiceImpl(final RestriccionEstandarUSPRepository restriccionEstandarUSPRepository,
										  final RestriccionEstandarMapper restriccionEstandarMapper) {
		super();
		this.restriccionEstandarUSPRepository = restriccionEstandarUSPRepository;
		this.restriccionEstandarMapper = restriccionEstandarMapper;
	}

	@Override
	public List<RestriccionEstandarDTO> listarRestricciones(Integer idDetalleMision) throws ServiceException {
		try {
			List<RestriccionEstandarEntity> lstEntity = restriccionEstandarUSPRepository.listarRestricciones(idDetalleMision);
			List<RestriccionEstandarDTO> lstDTO = restriccionEstandarMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(RestriccionEstandarDTO dto) throws ServiceException {
		try { 
			String mensaje = restriccionEstandarUSPRepository.guardar(restriccionEstandarMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
