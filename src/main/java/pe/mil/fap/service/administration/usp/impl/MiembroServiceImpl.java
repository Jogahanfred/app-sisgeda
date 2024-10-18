package pe.mil.fap.service.administration.usp.impl;
 
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.entity.administration.MiembroEntity;
import pe.mil.fap.mappers.administration.inf.MiembroMapper;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.repository.administration.usp.inf.MiembroUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class MiembroServiceImpl implements MiembroService {

	private final MiembroMapper miembroMapper;
	private final MiembroUSPRepository miembroUSPRepository;
	
	private final PersonalService personalService;

	public MiembroServiceImpl(final MiembroUSPRepository miembroUSPRepository, final MiembroMapper miembroMapper, final PersonalService personalService) {
		super();
		this.miembroMapper = miembroMapper;
		this.miembroUSPRepository = miembroUSPRepository;
		this.personalService = personalService;
	}

	@Override
	public List<MiembroDTO> listarMiembrosACalificarPorPeriodo(Integer idEscuadron, Integer nuPeriodo, String noTipoInstruccion)
			throws ServiceException {
		try {
			List<MiembroEntity> lstEntity = miembroUSPRepository.listarMiembrosACalificarPorPeriodo(idEscuadron, nuPeriodo, noTipoInstruccion); 
			List<MiembroDTO> lstDTO = miembroMapper.toListDTO(lstEntity);
			if (!lstDTO.isEmpty()) {
				for (MiembroDTO miembro : lstDTO) {
					miembro.setPersonal(personalService.buscarPorNsa(miembro.getCoNsa()).get());
				}
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<MiembroDTO> listarMiembros(Integer nuPeriodo, String noRol) throws ServiceException {
		try {
			List<MiembroEntity> lstEntity = miembroUSPRepository.listarMiembros(nuPeriodo, noRol); 
			List<MiembroDTO> lstDTO = miembroMapper.toListDTO(lstEntity);
			if (!lstDTO.isEmpty()) {
				for (MiembroDTO miembro : lstDTO) {
					miembro.setPersonal(personalService.buscarPorNsa(miembro.getCoNsa()).get());
				}
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<MiembroDTO> buscarPorId(Integer idMiembro) throws ServiceException {
		try { 
			Optional<MiembroEntity> optEntity = miembroUSPRepository.buscarPorId(idMiembro);
			Optional<MiembroDTO> optDto = Optional.of(miembroMapper.toDTO(optEntity.get()));
			if (!optDto.isEmpty()) { 
				optDto.get().setPersonal(personalService.buscarPorNsa(optDto.get().getCoNsa()).get());
				
			}
			return optDto;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<MiembroDTO> buscarPorNsaPorRolPeriodo(String coNsa, Integer nuPeriodo, String noRol) throws ServiceException {
		try { 
			Optional<MiembroEntity> optEntity = miembroUSPRepository.buscarPorNsaPorRolPeriodo(coNsa, nuPeriodo, noRol);
			Optional<MiembroDTO> optDto = Optional.of(miembroMapper.toDTO(optEntity.get()));
			if (!optDto.isEmpty()) { 
				optDto.get().setPersonal(personalService.buscarPorNsa(optDto.get().getCoNsa()).get());
				
			}
			return optDto; 
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Optional<MiembroDTO> buscarPorNsa(String coNsa) throws ServiceException {
		try { 
			Optional<MiembroEntity> optEntity = miembroUSPRepository.buscarPorNsa(coNsa);
			Optional<MiembroDTO> optDto = Optional.of(miembroMapper.toDTO(optEntity.get()));
			if (!optDto.isEmpty()) { 
				optDto.get().setPersonal(personalService.buscarPorNsa(optDto.get().getCoNsa()).get());
				
			}
			return optDto; 
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardarInstructor(MiembroDTO dto) throws ServiceException {
		try {   
			String mensaje = miembroUSPRepository.guardarInstructor(miembroMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) { 
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO guardarAlumno(MiembroDTO dto) throws ServiceException {
		try {   
			String mensaje = miembroUSPRepository.guardarAlumno(miembroMapper.toEntity(dto)); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) { 
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public MessageDTO deshabilitar(Integer id) throws ServiceException {
		try { 
			String mensaje = miembroUSPRepository.deshabilitar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = miembroUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
 
}
