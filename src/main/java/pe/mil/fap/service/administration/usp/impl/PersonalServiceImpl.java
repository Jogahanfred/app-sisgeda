package pe.mil.fap.service.administration.usp.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional; 
import pe.mil.fap.entity.administration.PersonalEntity;
import pe.mil.fap.mappers.administration.inf.PersonalMapper; 
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.MessageDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.administration.usp.inf.PersonalUSPRepository;
import pe.mil.fap.service.administration.usp.inf.PersonalService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class PersonalServiceImpl implements PersonalService{

	private final PersonalMapper personalMapper;
	private final PersonalUSPRepository personalUSPRepository; 

	public PersonalServiceImpl(final PersonalUSPRepository personalUSPRepository, final PersonalMapper personalMapper) {
		super();
		this.personalMapper = personalMapper;
		this.personalUSPRepository = personalUSPRepository;
	}  
	
	@Override
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws ServiceException {
		try { 
			return personalUSPRepository.paginar(parametro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<PersonalDTO> listar() throws ServiceException {
		try {
			List<PersonalEntity> lstEntity = personalUSPRepository.listar();
			List<PersonalDTO> lstDTO = personalMapper.toListDTO(lstEntity);
			return lstDTO;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<PersonalDTO> listarPorSiglaUnidad(String sigla) throws ServiceException {
		try {
			List<PersonalEntity> lstEntity = personalUSPRepository.listar();
			return lstEntity.stream()
		                    .filter(entity -> entity.getUnidadOrigen().equals(sigla.toUpperCase()))
		                    .sorted(Comparator.comparing(PersonalEntity::getOrden))
		                    .map(personalMapper::toDTO)
		                    .collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Optional<PersonalDTO> buscarPorNsa(String nsa) throws ServiceException {
		try { 
			Optional<PersonalEntity> optEntity = personalUSPRepository.buscarPorNsa(nsa);
			return Optional.of(personalMapper.toDTO(optEntity.get()));
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Transactional
	@Override
	public MessageDTO actualizarFotografia(PersonalDTO dto, MultipartFile archivo) throws ServiceException {
		MessageDTO msg = new MessageDTO();
		try {  
			Optional<PersonalDTO> optPersonal = this.buscarPorNsa(dto.getNsa());
	        if (archivo != null && !archivo.isEmpty()) {
	        		String mimeType = Files.probeContentType(Paths.get(archivo.getOriginalFilename()));
					
					if (mimeType == null || !mimeType.startsWith("image")) {
						throw new ServiceException("Error en el formato imagen");
					}
					
					String nombreArchivo = UUID.randomUUID().toString().concat("_")
							.concat(archivo.getOriginalFilename().replace(" ", ""));
					Path rutaArchivo = Paths.get("uploads/personal").resolve(nombreArchivo).toAbsolutePath();
	 
					Files.copy(archivo.getInputStream(), rutaArchivo);
	
					String rutaFotografiaAnterior = optPersonal.get().getFotografia();
					
					if (rutaFotografiaAnterior != null && rutaFotografiaAnterior.length() > 0) {
						Path rutaImagenFotografiaAnterior = Paths.get("uploads/personal").resolve(rutaFotografiaAnterior)
								.toAbsolutePath();
						File archivoFotografiaAnterior = rutaImagenFotografiaAnterior.toFile();
						if (archivoFotografiaAnterior.exists() && archivoFotografiaAnterior.canRead()) {
							archivoFotografiaAnterior.delete();
						}
					} 
					dto.setFotografia(nombreArchivo); 
	        	}else { 
		           dto.setFotografia(optPersonal.get().getFotografia());
		        }
		        String mensaje = personalUSPRepository.actualizarFotografia(personalMapper.toEntity(dto));
		        return msg = MessageDTO.builder().message(mensaje).build();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
