package pe.mil.fap.service.bussiness.usp.impl;
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.entity.helpers.CalificarMisionEntity;
import pe.mil.fap.exception.BadRequestException;
import pe.mil.fap.exception.ConflictException;
import pe.mil.fap.mappers.bussiness.inf.DetalleMisionMapper;
import pe.mil.fap.mappers.bussiness.inf.MisionMapper; 
import pe.mil.fap.model.bussiness.DetalleMisionDTO;
import pe.mil.fap.model.bussiness.MisionDTO;
import pe.mil.fap.model.helpers.FaseInscritoDTOResponse;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.MisionInscritoDTOResponse;
import pe.mil.fap.model.helpers.RegistroMisionDTORequest;
import pe.mil.fap.repository.bussiness.usp.inf.MisionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.bussiness.usp.inf.MisionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class MisionServiceImpl implements MisionService {

	private final MisionUSPRepository misionUSPRepository;
	private final MisionMapper misionMapper;
	private final DetalleMisionMapper detalleMisionMapper;
	private final MiembroService miembroService;

	public MisionServiceImpl(final MisionUSPRepository misionUSPRepository, 
							 final MisionMapper misionMapper,
							 final DetalleMisionMapper detalleMisionMapper,
							 final MiembroService miembroService) {
		super();
		this.misionUSPRepository = misionUSPRepository;
		this.misionMapper = misionMapper;
		this.detalleMisionMapper = detalleMisionMapper;
		this.miembroService = miembroService;
	}

	@Override
	public List<MisionInscritoDTOResponse> listarMisionesACalificarPorPeriodo(Integer nuPeriodo, Integer idMiembro,
			Integer idSubFase) throws ServiceException {
		try {
			List<CalificarMisionEntity> lstEntity = misionUSPRepository.listarMisionesACalificarPorPeriodo(nuPeriodo, idMiembro, idSubFase);
			List<MisionInscritoDTOResponse> lstDTO = new ArrayList<>();

	        for (CalificarMisionEntity mision : lstEntity) {
				MisionInscritoDTOResponse dto = new MisionInscritoDTOResponse();
				dto.setIdMision(mision.getIdMision());
				dto.setTxDescripcionTipoMision(mision.getTxDescripcionTipoMision());
				dto.setTxNombreTipoMision(mision.getTxNombreTipoMision());
				dto.setCoCodigo(mision.getCoCodigo());
				dto.setFlChequeo(mision.getFlChequeo());
				if (mision.getIdCalificador() != null) {
					dto.setCalificador(miembroService.buscarPorId(mision.getIdCalificador()).get());
				} 
				dto.setIdCalificacion(mision.getIdCalificacion());
				
				dto.setQtNota(mision.getQtNota());
				dto.setCoCalificacionVuelo(mision.getCoCalificacionVuelo());
				dto.setCoNroCola(mision.getCoNroCola());
				dto.setFeProgramacion(mision.getFeProgramacion());
				dto.setFeEjecucion(mision.getFeEjecucion());
	            lstDTO.add(dto);
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<MisionDTO> listarMisionesPorIdSubFase(Integer idSubFase) throws ServiceException {
		try {
			List<MisionEntity> lstEntity = misionUSPRepository.listarMisionesPorIdSubFase(idSubFase); 
			List<MisionDTO> lstDTO = misionMapper.toDTOList(lstEntity);
			lstDTO.forEach(mision -> {
				mision.setLstDetalleMision(detalleMisionMapper.toDTOList(misionUSPRepository.listarDetalleMisionPorIdMision(mision.getIdMision())));
			});
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<MisionDTO> buscarId(Integer id) throws ServiceException {
		try { 
			Optional<MisionEntity> optEntity = misionUSPRepository.buscarId(id);
			if(!optEntity.isPresent()){ 
				throw new Exception("Mision no existe");
			}
			MisionDTO dto = misionMapper.toDTO(optEntity.get());
			dto.setLstDetalleMision(detalleMisionMapper.toDTOList(misionUSPRepository.listarDetalleMisionPorIdMision(id)));
			return Optional.of(dto);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override 
	public MessageDTO registrarTransaccion(RegistroMisionDTORequest dto) throws ServiceException {
		try { 	 
			if (dto.getSubfase() == null || dto.getLstOperaciones() == null || dto.getLstMisiones() == null) {
			    throw new Exception("Proceso Inválido");
			}
			
		    Set<Integer> lstIdsManiobras = new HashSet<>();
			List<DetalleMisionDTO> lstDetalleMisionesDTO = new ArrayList<>();
			
			dto.getLstOperaciones().forEach(operacion -> {
				operacion.getLstManiobras().forEach(maniobra -> {	
					if (maniobra.getFlTransaccion() == 1) {		
						if (!lstIdsManiobras.add(maniobra.getIdManiobra())) { 
		                }
						DetalleMisionDTO detalleMision = new DetalleMisionDTO();
						detalleMision.setIdManiobra(maniobra.getIdManiobra());
						detalleMision.setIdEstandarRequerido(21);
						detalleMision.setFlBloqueado(1);
						detalleMision.setNuOrden(maniobra.getNuOrden());
						lstDetalleMisionesDTO.add(detalleMision);
					}
				});
			});
				
			Integer cantidadManiobras = dto.getSubfase().getNuTotalManiobra();
			if (lstDetalleMisionesDTO.size() != cantidadManiobras) { 
			    throw new BadRequestException("Cada misión debe tener " + cantidadManiobras + " maniobras, No " + lstDetalleMisionesDTO.size() + " maniobras." );
			}
						
			List<MisionDTO> lstMisionDTO = new ArrayList<>();
			Integer idSubFase = dto.getSubfase().getIdSubFase();
			dto.getLstMisiones().forEach(mision -> {
				if (mision.getIdTipoMision() == null || mision.getIdTipoMision() == 0) {
				    throw new BadRequestException("La misión " + mision.getCoCodigo() + " debe tener un tipo.");
					
				}
				MisionDTO obj = new MisionDTO();
				obj.setIdSubFase(idSubFase);
				obj.setIdTipoMision(mision.getIdTipoMision());
				obj.setCoCodigo(mision.getCoCodigo());
				obj.setFlChequeo(mision.getFlChequeo());
				obj.setFlBloqueado(1);
				obj.setFlEstado(1); 
				obj.setLstDetalleMision(lstDetalleMisionesDTO);
				lstMisionDTO.add(obj);
				
			});

		    Integer cantidadMisiones = dto.getSubfase().getNuTotalMision();
			if (lstMisionDTO.size() != cantidadMisiones) { 
			    throw new BadRequestException("Esta subfase debe tener " + cantidadMisiones + " misiones, NO " + lstMisionDTO.size() + " misiones.");
			}
			
			boolean chequeoSubFase = lstMisionDTO.stream()
				    							 .anyMatch(mision -> mision.getFlChequeo() == 1); 

			if (!chequeoSubFase) {
			    throw new BadRequestException("La Sub Fase debe contar con una misión de chequeo");
			}
 
			return MessageDTO.builder().message(misionUSPRepository.guardarTransaccion(misionMapper.toEntityList(lstMisionDTO))).build();
		} catch (Exception exception) { 
			if (exception instanceof RepositoryException) {
				throw (RepositoryException) exception; 
			}else if(exception instanceof ConflictException) {
				throw (ConflictException) exception;
			}else if(exception instanceof BadRequestException) {
				throw (BadRequestException) exception;
			}
			throw new ServiceException(exception.getMessage());
		}
		
	}

	@Override
	public MessageDTO actualizarEstandarPorIManiobraIdMision(Integer idMision, Integer idManiobra, Integer idEstandar)
			throws ServiceException {
		try { 
			String mensaje = misionUSPRepository.actualizarEstandarPorIManiobraIdMision(idMision, idManiobra, idEstandar);
			MessageDTO msg = MessageDTO.builder().message(mensaje).build(); 
			return msg;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
