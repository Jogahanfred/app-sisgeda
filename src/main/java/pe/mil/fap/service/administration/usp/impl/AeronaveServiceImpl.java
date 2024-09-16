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
import pe.mil.fap.entity.administration.AeronaveEntity; 
import pe.mil.fap.mappers.administration.inf.AeronaveMapper;
import pe.mil.fap.model.administration.AeronaveDTO; 
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.administration.usp.inf.AeronaveUSPRepository;
import pe.mil.fap.service.administration.usp.inf.AeronaveService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class AeronaveServiceImpl implements AeronaveService {

	private final AeronaveMapper aeronaveMapper;
	private final AeronaveUSPRepository aeronaveUSPRepository;

	public AeronaveServiceImpl(final AeronaveUSPRepository aeronaveUSPRepository, final AeronaveMapper aeronaveMapper) {
		super();
		this.aeronaveMapper = aeronaveMapper;
		this.aeronaveUSPRepository = aeronaveUSPRepository;
	}

	@Override
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws ServiceException {
		try { 
			return aeronaveUSPRepository.paginar(parametro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Optional<AeronaveDTO> buscarPorId(Integer id) throws ServiceException {
		try { 
			Optional<AeronaveEntity> optEntity = aeronaveUSPRepository.buscarId(id);
			return Optional.of(aeronaveMapper.toDTO(optEntity.get()));
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional
	@Override
	public MessageDTO guardar(AeronaveDTO dto, MultipartFile archivo) throws ServiceException {
		try { 		 
	        if (archivo != null && !archivo.isEmpty()) {
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/aeronave").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
				dto.setTxRutaImagen(nombreArchivo);
			} 

			String mensaje = aeronaveUSPRepository.guardar(aeronaveMapper.toEntity(dto));
			MessageDTO msg = MessageDTO.builder().message(mensaje).build();
			return msg;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional
	@Override
	public MessageDTO actualizar(AeronaveDTO dto, MultipartFile archivo) throws ServiceException {
		MessageDTO msg = new MessageDTO();
		try { 
			Optional<AeronaveDTO> optAeronave = this.buscarPorId(dto.getIdAeronave());
	        if (archivo != null && !archivo.isEmpty()) { 
				String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
				
				if (mimeType == null || !mimeType.startsWith("image")) {
					throw new ServiceException("Error en el formato imagen");
				}
				
				String nombreArchivo = UUID.randomUUID().toString().concat("_")
						.concat(archivo.getOriginalFilename().replace(" ", ""));
				Path rutaArchivo = Paths.get("uploads/aeronave").resolve(nombreArchivo).toAbsolutePath();
 
				Files.copy(archivo.getInputStream(), rutaArchivo);

				String rutaLogoAnterior = optAeronave.get().getTxRutaImagen();
				
				if (rutaLogoAnterior != null && rutaLogoAnterior.length() > 0) {
					Path rutaImagenLogoAnterior = Paths.get("uploads/aeronave").resolve(rutaLogoAnterior)
							.toAbsolutePath();
					File archivoLogoAnterior = rutaImagenLogoAnterior.toFile();
					if (archivoLogoAnterior.exists() && archivoLogoAnterior.canRead()) {
						archivoLogoAnterior.delete();
					}
				} 
				dto.setTxRutaImagen(nombreArchivo); 
	        }else { 
		        dto.setTxRutaImagen(optAeronave.get().getTxRutaImagen());
		    }  
			String mensaje = aeronaveUSPRepository.actualizar(aeronaveMapper.toEntity(dto));
			return msg = MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminar(Integer id) throws ServiceException {
		try { 
			String mensaje = aeronaveUSPRepository.eliminar(id);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MessageDTO eliminarMultiple(List<Integer> lstId) throws ServiceException {
		try { 
			String mensaje = aeronaveUSPRepository.eliminarMultiple(lstId);
			return MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
