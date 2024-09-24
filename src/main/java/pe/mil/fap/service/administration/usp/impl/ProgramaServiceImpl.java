package pe.mil.fap.service.administration.usp.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.ProgramaEntity;
import pe.mil.fap.mappers.administration.inf.ProgramaMapper;
import pe.mil.fap.model.administration.ProgramaDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ProgramaInscritoDTOResponse;
import pe.mil.fap.repository.administration.usp.inf.ProgramaUSPRepository;
import pe.mil.fap.service.administration.usp.inf.ProgramaService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class ProgramaServiceImpl implements ProgramaService {

	private final ProgramaMapper programaMapper;
	private final ProgramaUSPRepository programaUSPRepository;

	public ProgramaServiceImpl(final ProgramaUSPRepository programaUSPRepository, final ProgramaMapper programaMapper) {
		super();
		this.programaMapper = programaMapper;
		this.programaUSPRepository = programaUSPRepository;
	}

	@Override
	public List<ProgramaInscritoDTOResponse> listarProgramasACalificarPorPeriodo(Integer nuPeriodo,
			String noTipoInstruccion, Integer idMiembro) throws ServiceException {
		try {
			List<ProgramaEntity> lstEntity = programaUSPRepository.listarProgramasACalificarPorPeriodo(nuPeriodo, noTipoInstruccion, idMiembro);
			List<ProgramaInscritoDTOResponse> lstDTO = lstEntity.stream().map(programa -> {
				ProgramaInscritoDTOResponse dto = new ProgramaInscritoDTOResponse();
				dto.setIdPrograma(programa.getIdPrograma());
				dto.setNoNombre(programa.getNoNombre());
				dto.setNuPeriodo(programa.getNuPeriodo());
				dto.setTxDescripcion(programa.getTxDescripcion());
				dto.setTxDescripcionEscuadron(programa.getTxDescripcionEscuadron());
				dto.setTxFinalidad(programa.getTxFinalidad());
				return dto;
			}).collect(Collectors.toList());
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProgramaDTO> listarProgramas() throws ServiceException {
		try {
			List<ProgramaEntity> lstEntity = programaUSPRepository.listarProgramas(); 
			List<ProgramaDTO> lstDTO = programaMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<ProgramaDTO> listarProgramasPorIdUnidad(Integer idUnidad) throws ServiceException {
		try {
			List<ProgramaEntity> lstEntity = programaUSPRepository.listarProgramasPorIdUnidad(idUnidad);
			List<ProgramaDTO> lstDTO = programaMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<ProgramaDTO> listarProgramasPorIdEscuadron(Integer idEscuadron) throws ServiceException {
		try {
			List<ProgramaEntity> lstEntity = programaUSPRepository.listarProgramasPorIdEscuadron(idEscuadron);
			List<ProgramaDTO> lstDTO = programaMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public MessageDTO guardar(ProgramaDTO dto) throws ServiceException {
		try { 
			String mensaje = programaUSPRepository.guardar(programaMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(ProgramaDTO dto) throws ServiceException {
		try { 
			String mensaje = programaUSPRepository.actualizar(programaMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = programaUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = programaUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
 
}
