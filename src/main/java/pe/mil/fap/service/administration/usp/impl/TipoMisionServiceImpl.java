package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.TipoMisionEntity;
import pe.mil.fap.mappers.administration.inf.TipoMisionMapper;
import pe.mil.fap.model.administration.TipoMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.TipoMisionUSPRepository;
import pe.mil.fap.service.administration.usp.inf.TipoMisionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class TipoMisionServiceImpl implements TipoMisionService {

	private final TipoMisionMapper tipoMisionMapper;
	private final TipoMisionUSPRepository tipoMisionUSPRepository;

	public TipoMisionServiceImpl(final TipoMisionUSPRepository tipoMisionUSPRepository, final TipoMisionMapper tipoMisionMapper) {
		super();
		this.tipoMisionMapper = tipoMisionMapper;
		this.tipoMisionUSPRepository = tipoMisionUSPRepository;
	}

	@Override
	public List<TipoMisionDTO> listarTipoMisiones() throws ServiceException {
		try {
			List<TipoMisionEntity> lstEntity = tipoMisionUSPRepository.listarTipoMisiones(); 
			List<TipoMisionDTO> lstDTO = tipoMisionMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(TipoMisionDTO dto) throws ServiceException {
		try { 
			String mensaje = tipoMisionUSPRepository.guardar(tipoMisionMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(TipoMisionDTO dto) throws ServiceException {
		try { 
			String mensaje = tipoMisionUSPRepository.actualizar(tipoMisionMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = tipoMisionUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = tipoMisionUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
