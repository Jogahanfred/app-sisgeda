package pe.mil.fap.service.administration.usp.impl; 
 
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; 

import jakarta.transaction.Transactional; 
import pe.mil.fap.entity.administration.EscuadronEntity; 
import pe.mil.fap.mappers.administration.inf.EscuadronMapper; 
import pe.mil.fap.model.administration.EscuadronDTO; 
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.repository.administration.usp.inf.EscuadronUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.EscuadronService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class EscuadronServiceImpl implements EscuadronService {

	private final EscuadronMapper escuadronMapper;
	private final EscuadronUSPRepository escuadronUSPRepository;

	public EscuadronServiceImpl(final EscuadronUSPRepository escuadronUSPRepository, final EscuadronMapper escuadronMapper) {
		super();
		this.escuadronMapper = escuadronMapper;
		this.escuadronUSPRepository = escuadronUSPRepository;
	}

	@Override
	public List<EscuadronDTO> listarEscuadrones() throws ServiceException {
		try {
			List<EscuadronEntity> lstEntity = escuadronUSPRepository.listarEscuadrones(); 
		    List<EscuadronDTO> lstDTO = escuadronMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} 
	
	@Override
	public List<EscuadronDTO> listarEscuadronesPorIdUnidadRector(Integer idUnidad) throws ServiceException {
		try {
			List<EscuadronEntity> lstEntity = escuadronUSPRepository.listarEscuadronesPorIdUnidadRector(idUnidad); 
		    List<EscuadronDTO> lstDTO = escuadronMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<EscuadronDTO> listarEscuadronesPorIdUnidad(Integer idUnidad) throws ServiceException {
		try {
			List<EscuadronEntity> lstEntity = escuadronUSPRepository.listarEscuadronesPorIdUnidad(idUnidad); 
		    List<EscuadronDTO> lstDTO = escuadronMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Optional<EscuadronDTO> buscarId(Integer id) throws ServiceException {
		try { 
			Optional<EscuadronEntity> optEntity = escuadronUSPRepository.buscarId(id);
			return Optional.of(escuadronMapper.toDTO(optEntity.get()));
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional
	@Override
	public MessageDTO guardar(EscuadronDTO dto, MultipartFile archivo) throws ServiceException {
		try { 		 
	        if (archivo != null && !archivo.isEmpty()) {
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/escuadron").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
				dto.setTxRutaLogo(nombreArchivo); 
			} 

			String mensaje = escuadronUSPRepository.guardar(escuadronMapper.toEntity(dto));
			MessageDTO msg = MessageDTO.builder().message(mensaje).build();
			return msg;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional
	@Override
	public MessageDTO actualizar(EscuadronDTO dto, MultipartFile archivo) throws ServiceException {
		MessageDTO msg = new MessageDTO();
		try { 
			Optional<EscuadronDTO> optEscuadron = this.buscarId(dto.getIdEscuadron());
	        if (archivo != null && !archivo.isEmpty()) { 
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/escuadron").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);

				String rutaLogoAnterior = optEscuadron.get().getTxRutaLogo();
				
				if (rutaLogoAnterior != null && rutaLogoAnterior.length() > 0) {
					Path rutaImagenLogoAnterior = Paths.get("uploads/escuadron").resolve(rutaLogoAnterior)
							.toAbsolutePath();
					File archivoLogoAnterior = rutaImagenLogoAnterior.toFile();
					if (archivoLogoAnterior.exists() && archivoLogoAnterior.canRead()) {
						archivoLogoAnterior.delete();
					}
				} 
				dto.setTxRutaLogo(nombreArchivo); 
	        }else { 
		        dto.setTxRutaLogo(optEscuadron.get().getTxRutaLogo());
		    }  
			String mensaje = escuadronUSPRepository.actualizar(escuadronMapper.toEntity(dto));
			return msg = MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = escuadronUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = escuadronUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}   
}
