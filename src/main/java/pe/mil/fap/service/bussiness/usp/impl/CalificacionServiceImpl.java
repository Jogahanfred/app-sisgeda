package pe.mil.fap.service.bussiness.usp.impl;
 
import java.util.ArrayList; 
import java.util.List;
import java.util.Objects;
import java.util.Optional; 
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.common.enums.SeveridadEnum;
import pe.mil.fap.common.utils.UtilHelpers;
import pe.mil.fap.entity.helpers.EjeInterseccionACalificarEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.entity.helpers.EjeYEntity;
import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.exception.BadRequestException;
import pe.mil.fap.exception.ConflictException;
import pe.mil.fap.exception.NotFoundException;
import pe.mil.fap.mappers.bussiness.inf.CalificacionMapper;
import pe.mil.fap.mappers.helpers.inf.EjeInterseccionACalificarMapper;
import pe.mil.fap.mappers.helpers.inf.EjeInterseccionMapper;
import pe.mil.fap.mappers.helpers.inf.EjeXMapper;
import pe.mil.fap.mappers.helpers.inf.EjeYMapper;
import pe.mil.fap.mappers.helpers.inf.InscripcionMisionMapper;
import pe.mil.fap.model.administration.EstandarDTO;
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.bussiness.CalificacionDTO;
import pe.mil.fap.model.bussiness.DetalleCalificacionDTO; 
import pe.mil.fap.model.bussiness.MisionDTO;
import pe.mil.fap.model.helpers.EjeInterseccionACalificarDTO;
import pe.mil.fap.model.helpers.EjeInterseccionDTO;
import pe.mil.fap.model.helpers.EjeXDTO;
import pe.mil.fap.model.helpers.EjeYDTO;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
import pe.mil.fap.model.helpers.MatrizMisionDTO;
import pe.mil.fap.model.helpers.MatrizSubFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest;
import pe.mil.fap.repository.bussiness.usp.inf.CalificacionUSPRepository; 
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.administration.usp.inf.EstandarService;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.bussiness.usp.inf.CalificacionService;
import pe.mil.fap.service.bussiness.usp.inf.GrupoService;
import pe.mil.fap.service.bussiness.usp.inf.MisionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class CalificacionServiceImpl implements CalificacionService {

	private final CalificacionUSPRepository calificacionUSPRepository;

	private final MisionService misionService;
	private final GrupoService grupoService; 
	private final MiembroService miembroService;
	private final EstandarService estandarService;
	
	private final CalificacionMapper calificacionMapper;
	private final InscripcionMisionMapper inscripcionMisionMapper;
	private final EjeXMapper ejeXMapper;
	private final EjeYMapper ejeYMapper;
	private final EjeInterseccionACalificarMapper ejeInterseccionACalificacionMapper;

	
	public CalificacionServiceImpl(final EstandarService estandarService, 
								   final MisionService misionService,
								   final CalificacionUSPRepository calificacionUSPRepository,
								   final CalificacionMapper calificacionMapper,
								   final InscripcionMisionMapper inscripcionMisionMapper,
								   final GrupoService grupoService,
								   final EjeXMapper ejeXMapper,
								   final EjeYMapper ejeYMapper,
								   final EjeInterseccionACalificarMapper ejeInterseccionACalificacionMapper,
								   final MiembroService miembroService) {
		super();
		this.estandarService = estandarService;
		this.calificacionUSPRepository = calificacionUSPRepository;
		this.misionService = misionService;
		this.calificacionMapper = calificacionMapper;
		this.inscripcionMisionMapper = inscripcionMisionMapper;
		this.grupoService = grupoService;
		this.ejeXMapper = ejeXMapper;
		this.ejeYMapper = ejeYMapper;
		this.ejeInterseccionACalificacionMapper = ejeInterseccionACalificacionMapper;
		this.miembroService = miembroService;
	}
	
	@Override 
	public MessageDTO registrarTransaccion(RegistroCalificacionDTORequest dto) throws ServiceException {
		try { 	  
			List<Integer> lstIdAlumnos = new ArrayList<>();
			if (dto.getIdGrupo() != null ) {				
				Optional<GrupoDTO> optGrupo = grupoService.buscarId(dto.getIdGrupo());
				lstIdAlumnos = optGrupo.get().getLstAlumnos().stream().map(alumno -> alumno.getIdMiembro()).collect(Collectors.toList());
			}
					
			Boolean registroGrupal = false;
			 			
			List<CalificacionDTO> lstCalificaciones = new ArrayList<>(); 
			
			if ((dto.getIdInstructor() != null && lstIdAlumnos != null && lstIdAlumnos.isEmpty()) ||
				    (dto.getIdInstructor() == null && lstIdAlumnos != null && !lstIdAlumnos.isEmpty())) {
 
					Optional<MisionDTO> optMision = misionService.buscarId(dto.getIdMision());
					if (dto.getIdInstructor() != null) { 
						if(this.verificarInscripcionMision(optMision.get().getIdMision(), dto.getIdInstructor().toString())) {
							throw new ConflictException("El instructor ya esta registrado en esta misión");
						} 
				    	
						registroGrupal = false;

				    	CalificacionDTO calificacion = new CalificacionDTO();
				        calificacion.setIdMision(optMision.get().getIdMision());
				        calificacion.setIdCalificado(dto.getIdInstructor()); 
				            
				        optMision.get().getLstDetalleMision().forEach(detalleMision -> {
				        	DetalleCalificacionDTO detalleCalificacion = new DetalleCalificacionDTO();
				            detalleCalificacion.setIdManiobra(detalleMision.getIdManiobra());
				            calificacion.getLstDetalleCalificacion().add(detalleCalificacion);
				        });
				            
				        lstCalificaciones.add(calificacion);
				    }  
				    else if (!lstIdAlumnos.isEmpty()) { 
						if(this.verificarInscripcionMision(optMision.get().getIdMision(), UtilHelpers.convertListIntegerToString(lstIdAlumnos))) {
							throw new ConflictException("El grupo ya esta registrado en esta misión");
						}
						
				    	registroGrupal = true; 
		                for (Integer idMiembro : lstIdAlumnos) {
		                	CalificacionDTO calificacion = new CalificacionDTO();
				            calificacion.setIdMision(optMision.get().getIdMision());
				            calificacion.setIdCalificado(idMiembro);
				                
				            optMision.get().getLstDetalleMision().forEach(detalleMision -> {
				            	DetalleCalificacionDTO detalleCalificacion = new DetalleCalificacionDTO();
				                detalleCalificacion.setIdManiobra(detalleMision.getIdManiobra());
				                calificacion.getLstDetalleCalificacion().add(detalleCalificacion);
				            });
				            lstCalificaciones.add(calificacion);
				        }; 
				    }
				} else {
				    throw new Exception("Ha ocurrido un error: Debe enviar un instructor o una lista de alumnos, pero no ambos.");
				}
			return MessageDTO.builder().message(calificacionUSPRepository.guardarTransaccion(calificacionMapper.toEntityList(lstCalificaciones),registroGrupal)).build();
		}catch (Exception exception) { 
			if (exception instanceof RepositoryException) {
				throw (RepositoryException) exception; 
			}else if(exception instanceof ConflictException) {
				throw (ConflictException) exception;
			}else if(exception instanceof NotFoundException) {
				throw (NotFoundException) exception;
			}else if(exception instanceof BadRequestException) {
				throw (BadRequestException) exception;
			}
			throw new ServiceException(exception.getMessage());
		}
		 
	}

	@Override
	public Boolean verificarInscripcionMision(Integer idMision, String lstIds) throws ServiceException {
		try { 
			return calificacionUSPRepository.verificarInscripcionMision(idMision, lstIds) == 1 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<InscripcionMisionDTO> verificarInscripcionSubFase(Integer idSubFase, String lstIds) throws ServiceException {
		try { 
			List<InscripcionMisionEntity> lstEntity = calificacionUSPRepository.verificarInscripcionSubFase(idSubFase, lstIds); 
			List<InscripcionMisionDTO> lstDTO = inscripcionMisionMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO asignarInstructor(Integer idCalificador, Integer idCalificacion) throws ServiceException {
		try { 
			String mensaje = calificacionUSPRepository.asignarInstructor(idCalificador, idCalificacion);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO obtenerMatrizACalificar(Integer idCalificado, String coNsaUsuarioLogeado) throws ServiceException {
		try { 			
			Optional<MiembroDTO> optMiembro = miembroService.buscarPorNsa(coNsaUsuarioLogeado);
			if (optMiembro.isEmpty()) { 
				throw new Exception("No existe miembro");
			}
			MatrizMisionDTO matriz = new MatrizMisionDTO();
			
			List<EjeXEntity> lstEjeXEntity = calificacionUSPRepository.listarEjeX(idCalificado); 
			List<EjeYEntity> lstEjeYEntity = calificacionUSPRepository.listarEjeY(idCalificado); 
			List<EjeInterseccionACalificarEntity> lstInterseccionEntity = calificacionUSPRepository.listarEjeInterseccionACalificar(idCalificado);

			List<EjeXDTO> lstEjeXDTO = ejeXMapper.toListDTO(lstEjeXEntity); 
			List<EjeYDTO> lstEjeYDTO = ejeYMapper.toListDTO(lstEjeYEntity);
			List<EjeInterseccionACalificarDTO> lstInterseccionDTO = ejeInterseccionACalificacionMapper.toListDTO(lstInterseccionEntity);
			
			if (lstEjeXDTO.size() == 0 || lstEjeYDTO.size() == 0 || lstInterseccionDTO.size() == 0) {
				return MessageDTO.builder().type(SeveridadEnum.WARNING.getValor())
										   .message("La sub fase no tiene información para poder generar matriz")
										   .build();
			}
			lstEjeXDTO.forEach(ejeX -> ejeX.setFlHabilitado(Objects.equals(ejeX.getIdCalificador(), optMiembro.get().getIdMiembro()) ? 1 : 0));			
			
			matriz.setLstEjeX(lstEjeXDTO);
			matriz.setLstEjeY(lstEjeYDTO);
			matriz.setLstEjeInterseccion(recalcularCalificacionMision(lstInterseccionDTO));
			  
			return MessageDTO.builder().type(SeveridadEnum.SUCCESS.getValor())
									   .message("La matriz ha sido generada correctamente")
									   .data(matriz)
									   .build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO obtenerMatrizACalificarPorIdCalificacion(Integer idCalificado, Integer idCalificacion)
			throws ServiceException {
		try { 			
			MatrizMisionDTO matriz = new MatrizMisionDTO();
			
			List<EjeXEntity> lstEjeXEntity = calificacionUSPRepository.listarEjeXPorIdCalificacion(idCalificado, idCalificacion); 
			List<EjeYEntity> lstEjeYEntity = calificacionUSPRepository.listarEjeY(idCalificado); 
			List<EjeInterseccionACalificarEntity> lstInterseccionEntity = calificacionUSPRepository.listarEjeInterseccionACalificarPorIdCalificacion(idCalificado, idCalificacion);

			List<EjeXDTO> lstEjeXDTO = ejeXMapper.toListDTO(lstEjeXEntity); 
			List<EjeYDTO> lstEjeYDTO = ejeYMapper.toListDTO(lstEjeYEntity);
			List<EjeInterseccionACalificarDTO> lstInterseccionDTO = ejeInterseccionACalificacionMapper.toListDTO(lstInterseccionEntity);
			
			if (lstEjeXDTO.size() == 0 || lstEjeYDTO.size() == 0 || lstInterseccionDTO.size() == 0) {
				return MessageDTO.builder().type(SeveridadEnum.WARNING.getValor())
										   .message("La sub fase no tiene información para poder generar matriz")
										   .build();
			}
			
			matriz.setLstEjeX(lstEjeXDTO);
			matriz.setLstEjeY(lstEjeYDTO);
			matriz.setLstEjeInterseccion(recalcularCalificacionMision(lstInterseccionDTO));
			  
			return MessageDTO.builder().type(SeveridadEnum.SUCCESS.getValor())
									   .message("La matriz ha sido generada correctamente")
									   .data(matriz)
									   .build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} 
	
	@Override
	public MessageDTO calificarManiobra(Integer idManiobra, Integer idCalificacion, String coEstandarObtenido)
			throws ServiceException {
		try { 
			EstandarDTO estandar = estandarService.buscarPorCodigo(coEstandarObtenido).get();
			String mensaje = calificacionUSPRepository.calificarManiobra(idManiobra, idCalificacion, estandar.getIdEstandar());
			return MessageDTO.builder().message(mensaje).build(); 
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public List<EjeInterseccionACalificarDTO> recalcularCalificacionMision(List<EjeInterseccionACalificarDTO> lstEjeInterseccion) throws ServiceException{
		try {
			lstEjeInterseccion.stream().forEach(eje -> eje.validarEstadoManiobra());
			return lstEjeInterseccion;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
