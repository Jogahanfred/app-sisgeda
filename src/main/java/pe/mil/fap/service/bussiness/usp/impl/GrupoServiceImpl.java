package pe.mil.fap.service.bussiness.usp.impl;
  
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
 
import pe.mil.fap.entity.administration.GrupoEntity;
import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.mappers.bussiness.inf.GrupoMapper;
import pe.mil.fap.mappers.bussiness.inf.GrupoMiembroMapper;
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.administration.MiembroDTO;
import pe.mil.fap.model.bussiness.GrupoMiembroDTO;
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.repository.bussiness.usp.inf.GrupoUSPRepository;
import pe.mil.fap.service.administration.usp.inf.MiembroService;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.bussiness.usp.inf.GrupoService;
import pe.mil.fap.service.exception.ServiceException; 

@Service
public class GrupoServiceImpl implements GrupoService {
	
	private final MiembroService miembroService;
	private final PersonalService personalService;
	private final GrupoUSPRepository grupoUSPRepository;
	private final GrupoMapper grupoMapper;

	private final GrupoMiembroMapper grupoMiembroMapper;
	
	public GrupoServiceImpl(final GrupoUSPRepository grupoUSPRepository, 
						    final GrupoMapper grupoMapper, 
						    final GrupoMiembroMapper grupoMiembroMapper,
						    final MiembroService miembroService,
						    final PersonalService personalService) {
		super();
		this.grupoUSPRepository = grupoUSPRepository;
		this.grupoMapper = grupoMapper;
		this.grupoMiembroMapper = grupoMiembroMapper;
		this.miembroService = miembroService;
		this.personalService = personalService;
	}

	@Override
	public List<GrupoDTO> listar(Integer nuPeriodo, Integer idUnidad) throws ServiceException {
		try {
			List<GrupoEntity> lstEntity = grupoUSPRepository.listar(nuPeriodo, idUnidad); 
			List<GrupoDTO> lstDTO = grupoMapper.toListDTO(lstEntity);
			for (GrupoDTO grupoDTO : lstDTO) {				
				grupoDTO.setLstAlumnos(this.listarDetalle(grupoDTO.getIdGrupo()));
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<GrupoMiembroDTO> listarDetalle(Integer idGrupo) throws ServiceException {
		try {
			List<GrupoMiembroEntity> lstEntity = grupoUSPRepository.listarDetalle(idGrupo); 
			List<GrupoMiembroDTO> lstDTO = grupoMiembroMapper.toDTOList(lstEntity);
			for (GrupoMiembroDTO miembro : lstDTO) {
				Optional<MiembroDTO> optMiembro = miembroService.buscarPorId(miembro.getIdMiembro());
				miembro.setPersonal(personalService.buscarPorNsa(optMiembro.get().getCoNsa()).get());
			}
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO guardar(GrupoDTO dto) throws ServiceException {
		try { 
			String mensaje = grupoUSPRepository.guardar(grupoMapper.toEntity(dto), dto.getLstIdsAlumnos()); 
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	
}
