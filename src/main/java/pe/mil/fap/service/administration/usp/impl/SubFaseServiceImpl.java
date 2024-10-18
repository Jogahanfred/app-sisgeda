package pe.mil.fap.service.administration.usp.impl;
 
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.entity.administration.SubFaseEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity; 
import pe.mil.fap.mappers.administration.inf.SubFaseMapper;
import pe.mil.fap.mappers.helpers.inf.EjeInterseccionMapper;
import pe.mil.fap.mappers.helpers.inf.EjeXMapper;
import pe.mil.fap.mappers.helpers.inf.EjeYMapper;
import pe.mil.fap.model.administration.SubFaseDTO;
import pe.mil.fap.model.helpers.EjeInterseccionDTO;
import pe.mil.fap.model.helpers.EjeXDTO;
import pe.mil.fap.model.helpers.EjeYDTO;
import pe.mil.fap.model.helpers.FaseInscritoDTOResponse;
import pe.mil.fap.model.helpers.MatrizSubFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.SubFaseInscritoDTOResponse;
import pe.mil.fap.repository.administration.usp.inf.SubFaseUSPRepository;
import pe.mil.fap.service.administration.usp.inf.RestriccionEstandarService;
import pe.mil.fap.service.administration.usp.inf.SubFaseService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class SubFaseServiceImpl implements SubFaseService {

	private final EjeXMapper ejeXMapper;
	private final EjeYMapper ejeYMapper;
	private final EjeInterseccionMapper ejeInterseccionMapper;
	private final SubFaseMapper subFaseMapper;
	private final SubFaseUSPRepository subFaseUSPRepository;
	private final RestriccionEstandarService restriccionEstandarService;

	public SubFaseServiceImpl(final SubFaseUSPRepository subFaseUSPRepository, 
							  final SubFaseMapper subFaseMapper, 
							  final EjeXMapper ejeXMapper,
							  final EjeYMapper ejeYMapper,
							  final EjeInterseccionMapper ejeInterseccionMapper,
							  final RestriccionEstandarService restriccionEstandarService) {
		super();
		this.subFaseMapper = subFaseMapper;
		this.subFaseUSPRepository = subFaseUSPRepository;
		this.ejeXMapper = ejeXMapper;
		this.ejeYMapper = ejeYMapper;
		this.ejeInterseccionMapper = ejeInterseccionMapper;
		this.restriccionEstandarService = restriccionEstandarService;
	}

	@Override
	public List<SubFaseDTO> listarSubFases() throws ServiceException {
		try {
			List<SubFaseEntity> lstEntity = subFaseUSPRepository.listarSubFases(); 
			List<SubFaseDTO> lstDTO = subFaseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<SubFaseInscritoDTOResponse> listarSubFasesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro,
			Integer idFase) throws ServiceException {
		try {
			List<SubFaseEntity> lstEntity = subFaseUSPRepository.listarSubFasesACalificarPorPeriodo(nuPeriodo, idMiembro, idFase);
			List<SubFaseInscritoDTOResponse> lstDTO = lstEntity.stream().map(subFase -> {
				SubFaseInscritoDTOResponse dto = new SubFaseInscritoDTOResponse();
				dto.setIdSubFase(subFase.getIdSubFase());
				dto.setNoNombre(subFase.getTxDescripcionSubFase());
				dto.setNuTotalHora(subFase.getNuTotalHora());
				dto.setNuTotalMision(subFase.getNuTotalMision());
				dto.setNuTotalManiobra(subFase.getNuTotalManiobra());
				dto.setCoCodigo(subFase.getCoCodigo());
				return dto;
			}).collect(Collectors.toList());
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SubFaseDTO> listarSubFasesPorIdUnidad(Integer idUnidad) throws ServiceException {
		try {
			List<SubFaseEntity> lstEntity = subFaseUSPRepository.listarSubFasesPorIdUnidad(idUnidad); 
			List<SubFaseDTO> lstDTO = subFaseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<SubFaseDTO> listarSubFasesPorIdFase(Integer idFase) throws ServiceException {
		try {
			List<SubFaseEntity> lstEntity = subFaseUSPRepository.listarSubFasesPorIdFase(idFase); 
			List<SubFaseDTO> lstDTO = subFaseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<Integer, String>> listarFiltroPeriodo(Integer nuPeriodo) throws ServiceException {
		try {
			List<Map<Integer, String>> lstFilro = subFaseUSPRepository.listarFiltroPeriodo(nuPeriodo); 
			return lstFilro;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public MessageDTO obtenerMatriz(Integer id) throws ServiceException {
		try { 			
			MatrizSubFaseDTO matriz = new MatrizSubFaseDTO();
			
			List<EjeXEntity> lstEjeXEntity = subFaseUSPRepository.listarEjeX(id); 
			List<EjeYEntity> lstEjeYEntity = subFaseUSPRepository.listarEjeY(id); 
			List<EjeInterseccionEntity> lstInterseccionEntity = subFaseUSPRepository.listarEjeInterseccion(id); 

			List<EjeXDTO> lstEjeXDTO = ejeXMapper.toListDTO(lstEjeXEntity); 
			List<EjeYDTO> lstEjeYDTO = ejeYMapper.toListDTO(lstEjeYEntity);
			List<EjeInterseccionDTO> lstInterseccionDTO = ejeInterseccionMapper.toListDTO(lstInterseccionEntity);
			
			if (lstEjeXDTO.size() == 0 || lstEjeYDTO.size() == 0 || lstInterseccionDTO.size() == 0) {
				return MessageDTO.builder().type(SeveridadEnum.WARNING.getValor())
										   .message("La sub fase no tiene información para poder generar matriz")
										   .build();
			}
			
			matriz.setLstEjeX(lstEjeXDTO);
			matriz.setLstEjeY(lstEjeYDTO);
			matriz.setLstEjeInterseccion(lstInterseccionDTO);
			  
			return MessageDTO.builder().type(SeveridadEnum.SUCCESS.getValor())
									   .message("La matriz ha sido generada correctamente")
									   .data(matriz)
									   .build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}  
	
	@Override
	public MessageDTO obtenerMatrizRestriccion(Integer id) throws ServiceException {
		try { 			
			MatrizSubFaseDTO matriz = new MatrizSubFaseDTO();
			
			List<EjeXEntity> lstEjeXEntity = subFaseUSPRepository.listarEjeX(id); 
			List<EjeYEntity> lstEjeYEntity = subFaseUSPRepository.listarEjeY(id); 
			List<EjeInterseccionEntity> lstInterseccionEntity = subFaseUSPRepository.listarEjeInterseccion(id); 

			List<EjeXDTO> lstEjeXDTO = ejeXMapper.toListDTO(lstEjeXEntity); 
			List<EjeYDTO> lstEjeYDTO = ejeYMapper.toListDTO(lstEjeYEntity);
			List<EjeInterseccionDTO> lstInterseccionDTO = ejeInterseccionMapper.toListDTO(lstInterseccionEntity);
			
			if (lstEjeXDTO.size() == 0 || lstEjeYDTO.size() == 0 || lstInterseccionDTO.size() == 0) {
				return MessageDTO.builder().type(SeveridadEnum.WARNING.getValor())
										   .message("La sub fase no tiene información para poder generar matriz")
										   .build();
			}
			
			matriz.setLstEjeX(lstEjeXDTO);
			matriz.setLstEjeY(lstEjeYDTO);
			
			lstInterseccionDTO.removeIf(interseccion -> interseccion.getIdEstandarRequerido() == 21);
			
			for (EjeInterseccionDTO interseccion : lstInterseccionDTO) {
				interseccion.setLstRestricciones(restriccionEstandarService.listarRestricciones(interseccion.getIdDetalleMision()));
			}
			
			matriz.setLstEjeInterseccion(lstInterseccionDTO);
			  
			return MessageDTO.builder().type(SeveridadEnum.SUCCESS.getValor())
									   .message("La matriz - restricciones han sido consultada correctamente")
									   .data(matriz)
									   .build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}  
  
	@Override
	public Optional<SubFaseDTO> buscarPorId(Integer id) throws ServiceException {
		try {
			Optional<SubFaseEntity> entity = subFaseUSPRepository.buscarPorId(id); 
			if (entity.isEmpty()) {
				return Optional.empty();
			}
			SubFaseDTO dto = subFaseMapper.toDTO(entity.get());
			return Optional.of(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public MessageDTO guardar(SubFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = subFaseUSPRepository.guardar(subFaseMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(SubFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = subFaseUSPRepository.actualizar(subFaseMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = subFaseUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = subFaseUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}  
}
