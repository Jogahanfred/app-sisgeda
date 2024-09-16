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
import pe.mil.fap.entity.administration.UnidadEntity; 
import pe.mil.fap.mappers.administration.inf.UnidadMapper; 
import pe.mil.fap.model.administration.UnidadDTO;
import pe.mil.fap.model.helpers.MessageDTO; 
import pe.mil.fap.repository.administration.usp.inf.UnidadUSPRepository; 
import pe.mil.fap.service.administration.usp.inf.UnidadService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class UnidadServiceImpl implements UnidadService {

	private final UnidadMapper unidadMapper;
	private final UnidadUSPRepository unidadUSPRepository;

	public UnidadServiceImpl(final UnidadUSPRepository unidadUSPRepository, final UnidadMapper unidadMapper) {
		super();
		this.unidadMapper = unidadMapper;
		this.unidadUSPRepository = unidadUSPRepository;
	}

	@Override
	public List<UnidadDTO> listarUnidades() throws ServiceException {
		try {
			List<UnidadEntity> lstEntity = unidadUSPRepository.listarUnidades(); 
			List<UnidadDTO> lstDTO = unidadMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<UnidadDTO> listarUnidadesPorRector(Integer nuCodigoRector) throws ServiceException {
		try {
			List<UnidadEntity> lstEntity = unidadUSPRepository.listarUnidadesPorRector(nuCodigoRector); 
			List<UnidadDTO> lstDTO = unidadMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} 
	
	@Override
	public Optional<UnidadDTO> buscarId(Integer id) throws ServiceException {
		try { 
			Optional<UnidadEntity> optEntity = unidadUSPRepository.buscarId(id);
			return Optional.of(unidadMapper.toDTO(optEntity.get()));
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional
	@Override
	public MessageDTO guardar(UnidadDTO dto, MultipartFile archivo) throws ServiceException {
		try { 		 
	        if (archivo != null && !archivo.isEmpty()) {
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/unidad").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
				dto.setTxRutaLogo(nombreArchivo); 
			}

			String mensaje = unidadUSPRepository.guardar(unidadMapper.toEntity(dto));
			MessageDTO msg = MessageDTO.builder().message(mensaje).build();
			return msg;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional
	@Override
	public MessageDTO actualizar(UnidadDTO dto, MultipartFile archivo) throws ServiceException {
		MessageDTO msg = new MessageDTO();
		try { 
			Optional<UnidadDTO> optUnidad = this.buscarId(dto.getIdUnidad());
	        if (archivo != null && !archivo.isEmpty()) {
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/unidad").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);

				String rutaLogoAnterior = optUnidad.get().getTxRutaLogo();
				
				if (rutaLogoAnterior != null && rutaLogoAnterior.length() > 0) {
					Path rutaImagenLogoAnterior = Paths.get("uploads/unidad").resolve(rutaLogoAnterior)
							.toAbsolutePath();
					File archivoLogoAnterior = rutaImagenLogoAnterior.toFile();
					if (archivoLogoAnterior.exists() && archivoLogoAnterior.canRead()) {
						archivoLogoAnterior.delete();
					}
				} 
				dto.setTxRutaLogo(nombreArchivo); 
	        }else { 
		        dto.setTxRutaLogo(optUnidad.get().getTxRutaLogo());
		    }
			String mensaje = unidadUSPRepository.actualizar(unidadMapper.toEntity(dto));
			return msg = MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = unidadUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = unidadUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
 
	/*private String actualizarLogo(MultipartFile archivo) throws ServiceException{
		if (!archivo.isEmpty()) {
			String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
			
			if (mimeType == null || !mimeType.startsWith("image")) {
				throw new ServiceException("El archivo no es una imagen");
			}
			
			String nombreArchivo = UUID.randomUUID().toString().concat("_")
					.concat(archivo.getOriginalFilename().replace(" ", ""));
			Path rutaArchivo = Paths.get("uploads/unidad").resolve(nombreArchivo).toAbsolutePath();

			Files.copy(archivo.getInputStream(), rutaArchivo);
	
			String nombreFotografiaAnterior = optPersona.get().getFotografia();

			if (nombreFotografiaAnterior != null && nombreFotografiaAnterior.length() > 0) {
				Path rutaFotografiaAnterior = Paths.get("uploads/unidad").resolve(nombreFotografiaAnterior)
						.toAbsolutePath();
				File archivoFotografiaAnterior = rutaFotografiaAnterior.toFile();
				if (archivoFotografiaAnterior.exists() && archivoFotografiaAnterior.canRead()) {
					archivoFotografiaAnterior.delete();
				}
			} 

			optPersona.get().setFotografia(nombreArchivo);
		}
	} */  
}
