package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.ManiobraEntity;
import pe.mil.fap.mappers.administration.inf.ManiobraMapper;
import pe.mil.fap.model.administration.ManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.ManiobraUSPRepository;
import pe.mil.fap.service.administration.usp.inf.ManiobraService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class ManiobraServiceImpl implements ManiobraService {

	private final ManiobraMapper maniobraMapper;
	private final ManiobraUSPRepository maniobraUSPRepository;

	public ManiobraServiceImpl(final ManiobraUSPRepository maniobraUSPRepository, final ManiobraMapper maniobraMapper) {
		super();
		this.maniobraMapper = maniobraMapper;
		this.maniobraUSPRepository = maniobraUSPRepository;
	}

	@Override
	public List<ManiobraDTO> listarManiobras() throws ServiceException {
		try {
			List<ManiobraEntity> lstEntity = maniobraUSPRepository.listarManiobras(); 
			List<ManiobraDTO> lstDTO = maniobraMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ManiobraDTO> listarManiobrasPorIdOperacion(Integer idOperacion) throws ServiceException {
		try {
			List<ManiobraEntity> lstEntity = maniobraUSPRepository.listarManiobrasPorIdOperacion(idOperacion); 
			List<ManiobraDTO> lstDTO = maniobraMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public MessageDTO guardar(ManiobraDTO dto) throws ServiceException {
		try { 
			String mensaje = maniobraUSPRepository.guardar(maniobraMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(ManiobraDTO dto) throws ServiceException {
		try { 
			String mensaje = maniobraUSPRepository.actualizar(maniobraMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = maniobraUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = maniobraUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
