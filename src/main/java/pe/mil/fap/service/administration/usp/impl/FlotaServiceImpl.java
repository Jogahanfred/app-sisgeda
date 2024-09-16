package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;
 
import pe.mil.fap.entity.administration.FlotaEntity;
import pe.mil.fap.mappers.administration.inf.FlotaMapper;
import pe.mil.fap.model.administration.FlotaDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.FlotaUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.FlotaService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class FlotaServiceImpl implements FlotaService {

	private final FlotaMapper flotaMapper;
	private final FlotaUSPRepository flotaUSPRepository;

	public FlotaServiceImpl(final FlotaUSPRepository flotaUSPRepository, final FlotaMapper flotaMapper) {
		super();
		this.flotaMapper = flotaMapper;
		this.flotaUSPRepository = flotaUSPRepository;
	}

	@Override
	public List<FlotaDTO> listarFlotas() throws ServiceException {
		try {
			List<FlotaEntity> lstEntity = flotaUSPRepository.listarFlotas(); 
			List<FlotaDTO> lstDTO = flotaMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(FlotaDTO dto) throws ServiceException {
		try { 
			String mensaje = flotaUSPRepository.guardar(flotaMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) { 
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO actualizar(FlotaDTO dto) throws ServiceException {
		try { 
			String mensaje = flotaUSPRepository.actualizar(flotaMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = flotaUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = flotaUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
