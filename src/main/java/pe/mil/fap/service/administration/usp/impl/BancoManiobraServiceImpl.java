package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.BancoManiobraEntity;
import pe.mil.fap.mappers.administration.inf.BancoManiobraMapper;
import pe.mil.fap.model.administration.BancoManiobraDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.BancoManiobraUSPRepository;
import pe.mil.fap.service.administration.usp.inf.BancoManiobraService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class BancoManiobraServiceImpl implements BancoManiobraService {

	private final BancoManiobraMapper bancoManiobraMapper;
	private final BancoManiobraUSPRepository bancoManiobraUSPRepository;

	public BancoManiobraServiceImpl(final BancoManiobraUSPRepository bancoManiobraUSPRepository, final BancoManiobraMapper bancoManiobraMapper) {
		super();
		this.bancoManiobraMapper = bancoManiobraMapper;
		this.bancoManiobraUSPRepository = bancoManiobraUSPRepository;
	}

	@Override
	public List<BancoManiobraDTO> listarBancoManiobras() throws ServiceException {
		try {
			List<BancoManiobraEntity> lstEntity = bancoManiobraUSPRepository.listarBancoManiobras(); 
			List<BancoManiobraDTO> lstDTO = bancoManiobraMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(BancoManiobraDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoManiobraUSPRepository.guardar(bancoManiobraMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(BancoManiobraDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoManiobraUSPRepository.actualizar(bancoManiobraMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = bancoManiobraUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = bancoManiobraUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
