package pe.mil.fap.service.administration.usp.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.BancoSubFaseEntity;
import pe.mil.fap.mappers.administration.inf.BancoSubFaseMapper;
import pe.mil.fap.model.administration.BancoSubFaseDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.BancoSubFaseUSPRepository;
import pe.mil.fap.service.administration.usp.inf.BancoSubFaseService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class BancoSubFaseServiceImpl implements BancoSubFaseService {

	private final BancoSubFaseMapper bancoSubFaseMapper;
	private final BancoSubFaseUSPRepository bancoSubFaseUSPRepository;

	public BancoSubFaseServiceImpl(final BancoSubFaseUSPRepository bancoSubFaseUSPRepository, final BancoSubFaseMapper bancoSubFaseMapper) {
		super();
		this.bancoSubFaseMapper = bancoSubFaseMapper;
		this.bancoSubFaseUSPRepository = bancoSubFaseUSPRepository;
	}

	@Override
	public List<BancoSubFaseDTO> listarBancoSubFases() throws ServiceException {
		try {
			List<BancoSubFaseEntity> lstEntity = bancoSubFaseUSPRepository.listarBancoSubFases(); 
			List<BancoSubFaseDTO> lstDTO = bancoSubFaseMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(BancoSubFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoSubFaseUSPRepository.guardar(bancoSubFaseMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO actualizar(BancoSubFaseDTO dto) throws ServiceException {
		try { 
			String mensaje = bancoSubFaseUSPRepository.actualizar(bancoSubFaseMapper.toEntity(dto));
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = bancoSubFaseUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = bancoSubFaseUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
