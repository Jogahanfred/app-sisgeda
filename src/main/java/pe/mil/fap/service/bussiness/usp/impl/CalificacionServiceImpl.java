package pe.mil.fap.service.bussiness.usp.impl;
 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Optional; 
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.common.utils.UtilHelpers; 
import pe.mil.fap.entity.helpers.InscripcionMisionEntity;
import pe.mil.fap.exception.BadRequestException;
import pe.mil.fap.exception.ConflictException;
import pe.mil.fap.exception.NotFoundException;
import pe.mil.fap.mappers.bussiness.inf.CalificacionMapper;
import pe.mil.fap.mappers.helpers.inf.InscripcionMisionMapper; 
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.bussiness.CalificacionDTO;
import pe.mil.fap.model.bussiness.DetalleCalificacionDTO; 
import pe.mil.fap.model.bussiness.MisionDTO;
import pe.mil.fap.model.helpers.InscripcionMisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest;
import pe.mil.fap.repository.bussiness.usp.inf.CalificacionUSPRepository; 
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.bussiness.usp.inf.CalificacionService;
import pe.mil.fap.service.bussiness.usp.inf.GrupoService;
import pe.mil.fap.service.bussiness.usp.inf.MisionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class CalificacionServiceImpl implements CalificacionService {

	private final MisionService misionService;
	private final CalificacionUSPRepository calificacionUSPRepository;
	private final CalificacionMapper calificacionMapper;
	private final InscripcionMisionMapper inscripcionMisionMapper;
	private final GrupoService grupoService; 
	
	public CalificacionServiceImpl(final MisionService misionService,
								   final CalificacionUSPRepository calificacionUSPRepository,
								   final CalificacionMapper calificacionMapper,
								   final InscripcionMisionMapper inscripcionMisionMapper,
								   final GrupoService grupoService) {
		super();
		this.calificacionUSPRepository = calificacionUSPRepository;
		this.misionService = misionService;
		this.calificacionMapper = calificacionMapper;
		this.inscripcionMisionMapper = inscripcionMisionMapper;
		this.grupoService = grupoService;
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

}
