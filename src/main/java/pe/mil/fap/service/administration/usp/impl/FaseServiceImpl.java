package pe.mil.fap.service.administration.usp.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.entity.administration.MiembroEntity;
import pe.mil.fap.entity.administration.ProgramaEntity;
import pe.mil.fap.mappers.administration.inf.FaseMapper;
import pe.mil.fap.model.administration.FaseDTO;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.helpers.FaseInscritoDTOResponse;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
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
	public List<FaseInscritoDTOResponse> listarFasesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro,
			Integer idPrograma) throws ServiceException {
		try {
			List<FaseEntity> lstEntity = faseUSPRepository.listarFasesACalificarPorPeriodo(nuPeriodo, idMiembro, idPrograma);
			List<FaseInscritoDTOResponse> lstDTO = lstEntity.stream().map(fase -> {
				FaseInscritoDTOResponse dto = new FaseInscritoDTOResponse();
				dto.setIdFase(fase.getIdFase());
				dto.setNoNombre(fase.getTxDescripcionFase());
				dto.setNuTotalHora(fase.getNuTotalHora());
				dto.setNuTotalSubFase(fase.getNuTotalSubFase());
				return dto;
			}).collect(Collectors.toList());
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
	public Optional<FaseDTO> buscarPorId(Integer id) throws ServiceException {
		try {
			Optional<FaseEntity> optEntity = faseUSPRepository.buscarId(id);
			return Optional.of(faseMapper.toDTO(optEntity.get()));
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
