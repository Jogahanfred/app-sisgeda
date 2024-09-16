package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.BancoFaseEntity;
import pe.mil.fap.mappers.administration.inf.BancoFaseMapper;
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.BancoFaseUSPRepository;
import pe.mil.fap.service.administration.usp.inf.BancoFaseService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class BancoFaseServiceImpl implements BancoFaseService {

	private final BancoFaseMapper bancoFaseMapper;
	private final BancoFaseUSPRepository bancoFaseUSPRepository;

	public BancoFaseServiceImpl(final BancoFaseUSPRepository bancoFaseUSPRepository, final BancoFaseMapper bancoFaseMapper) {
		super();
		this.bancoFaseMapper = bancoFaseMapper;
		this.bancoFaseUSPRepository = bancoFaseUSPRepository;
	}

	@Override
	public List<BancoFaseDTO> listarBancoFases() throws ServiceException {
		try {
			List<BancoFaseEntity> lstEntity = bancoFaseUSPRepository.listarBancoFases(); 
			List<BancoFaseDTO> lstDTO = bancoFaseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(BancoFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoFaseUSPRepository.guardar(bancoFaseMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(BancoFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoFaseUSPRepository.actualizar(bancoFaseMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = bancoFaseUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = bancoFaseUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
