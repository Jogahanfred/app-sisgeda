package pe.mil.fap.service.bussiness.usp.impl;
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import pe.mil.fap.exception.BadRequestException;
import pe.mil.fap.exception.ConflictException;
import pe.mil.fap.mappers.bussiness.inf.MisionMapper;
import pe.mil.fap.model.bussiness.DetalleMisionDTO;
import pe.mil.fap.model.bussiness.MisionDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.RegistroMisionDTORequest;
import pe.mil.fap.repository.bussiness.usp.inf.MisionUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;
import pe.mil.fap.service.bussiness.usp.inf.MisionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class MisionServiceImpl implements MisionService {

	private final MisionUSPRepository misionUSPRepository;
	private final MisionMapper misionMapper;

	public MisionServiceImpl(MisionUSPRepository misionUSPRepository, MisionMapper misionMapper) {
		super();
		this.misionUSPRepository = misionUSPRepository;
		this.misionMapper = misionMapper;
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
