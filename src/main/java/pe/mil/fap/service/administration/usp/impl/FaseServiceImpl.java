package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.mappers.administration.inf.FaseMapper;
import pe.mil.fap.model.administration.FaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.FaseUSPRepository;
import pe.mil.fap.service.administration.usp.inf.FaseService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class FaseServiceImpl implements FaseService {

	private final FaseMapper faseMapper;
	private final FaseUSPRepository faseUSPRepository;
	private final SubFaseServiceImpl subFaseServiceImpl;
	
	public FaseServiceImpl(final SubFaseServiceImpl subFaseServiceImpl, final FaseUSPRepository faseUSPRepository, final FaseMapper faseMapper) {
		super();
		this.faseMapper = faseMapper;
		this.faseUSPRepository = faseUSPRepository;
		this.subFaseServiceImpl = subFaseServiceImpl;
	}

	@Override
	public List<FaseDTO> listarFases() throws ServiceException {
		try {
			List<FaseEntity> lstEntity = faseUSPRepository.listarFases(); 
			List<FaseDTO> lstDTO = faseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<FaseDTO> listarFasesPorIdUnidad(Integer idUnidad) throws ServiceException {
		try {
			List<FaseEntity> lstEntity = faseUSPRepository.listarFasesPorIdUnidad(idUnidad); 
			List<FaseDTO> lstDTO = faseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<FaseDTO> listarFasesPorIdPrograma(Integer idPrograma) throws ServiceException {
		try {
			List<FaseEntity> lstEntity = faseUSPRepository.listarFasesPorIdPrograma(idPrograma); 
			List<FaseDTO> lstDTO = faseMapper.toListDTO(lstEntity);
			if (!lstDTO.isEmpty()) {
				for (FaseDTO fase : lstDTO) {
					fase.setLstSubFases(subFaseServiceImpl.listarSubFasesPorIdFase(fase.getIdFase()));
				}
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public MessageDTO guardar(FaseDTO dto) throws ServiceException {
		try { 
			String mensaje = faseUSPRepository.guardar(faseMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(FaseDTO dto) throws ServiceException {
		try { 
			String mensaje = faseUSPRepository.actualizar(faseMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = faseUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = faseUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
