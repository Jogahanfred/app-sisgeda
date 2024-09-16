package pe.mil.fap.service.administration.usp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.OperacionEntity; 
import pe.mil.fap.mappers.administration.inf.OperacionMapper;
import pe.mil.fap.model.administration.ManiobraDTO;
import pe.mil.fap.model.administration.OperacionDTO; 
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.OperacionDTOResponse;
import pe.mil.fap.repository.administration.usp.inf.OperacionUSPRepository;
import pe.mil.fap.service.administration.usp.inf.ManiobraService;
import pe.mil.fap.service.administration.usp.inf.OperacionService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class OperacionServiceImpl implements OperacionService {

	private final OperacionMapper operacionMapper;
	private final OperacionUSPRepository operacionUSPRepository;
	private final ManiobraService maniobraService;

	public OperacionServiceImpl(final ManiobraService maniobraService, final OperacionUSPRepository operacionUSPRepository, final OperacionMapper operacionMapper) {
		super();
		this.maniobraService = maniobraService;
		this.operacionMapper = operacionMapper;
		this.operacionUSPRepository = operacionUSPRepository;
	}

	@Override
	public List<OperacionDTO> listarOperaciones() throws ServiceException {
		try {
			List<OperacionEntity> lstEntity = operacionUSPRepository.listarOperaciones(); 
			List<OperacionDTO> lstDTO = operacionMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<OperacionDTO> listarOperacionesPorIdEscuadron(Integer idEscuadron) throws ServiceException {
		try {
			List<OperacionEntity> lstEntity = operacionUSPRepository.listarOperacionesPorIdEscuadron(idEscuadron); 
			List<OperacionDTO> lstDTO = operacionMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public List<OperacionDTOResponse> listarOperacionesDetalleManiobra(List<Integer> lstIdOperaciones) throws ServiceException{
		try {
			List<OperacionDTOResponse> lstOperacionDTOResponse = new ArrayList<>();
			lstIdOperaciones.forEach(id -> {
				try {
					Optional<OperacionDTO> optOperacion = this.buscarPorId(id);
					List<ManiobraDTO> lstManiobras = maniobraService.listarManiobrasPorIdOperacion(optOperacion.get().getIdOperacion());
					
					OperacionDTOResponse obj = new OperacionDTOResponse();
					obj.setIdOperacion(optOperacion.get().getIdOperacion());
					obj.setNoNombre(optOperacion.get().getNoNombre());
					
					List<OperacionDTOResponse.ManiobraBaseDTO> maniobrasResponse = lstManiobras.stream()
				            .map(maniobraDTO -> {
				                OperacionDTOResponse.ManiobraBaseDTO maniobraResponse = new OperacionDTOResponse.ManiobraBaseDTO();
				                maniobraResponse.setIdManiobra(maniobraDTO.getIdManiobra());
				                maniobraResponse.setTxDescripcionBancoManiobra(maniobraDTO.getTxDescripcionBancoManiobra());
				                return maniobraResponse;
				            })
				            .collect(Collectors.toList());
					
					
					obj.setLstManiobras(maniobrasResponse);
					
					lstOperacionDTOResponse.add(obj);
				} catch (ServiceException e) { 
					e.printStackTrace();
				}
			});
			return lstOperacionDTOResponse;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Optional<OperacionDTO> buscarPorId(Integer id) throws ServiceException {
		try {
			Optional<OperacionEntity> entity = operacionUSPRepository.buscarPorId(id); 
			OperacionDTO dto = operacionMapper.toDTO(entity.get());
			return Optional.of(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public MessageDTO guardar(OperacionDTO dto) throws ServiceException {
		try { 
			String mensaje = operacionUSPRepository.guardar(operacionMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(OperacionDTO dto) throws ServiceException {
		try { 
			String mensaje = operacionUSPRepository.actualizar(operacionMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = operacionUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = operacionUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
