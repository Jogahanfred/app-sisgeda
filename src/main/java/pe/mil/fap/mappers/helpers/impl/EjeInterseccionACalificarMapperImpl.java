package pe.mil.fap.mappers.helpers.impl;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionACalificarEntity;
import pe.mil.fap.mappers.administration.inf.RestriccionEstandarMapper;
import pe.mil.fap.mappers.helpers.inf.EjeInterseccionACalificarMapper;
import pe.mil.fap.model.administration.RestriccionEstandarDTO;
import pe.mil.fap.model.helpers.EjeInterseccionACalificarDTO;
 
@Component
public class EjeInterseccionACalificarMapperImpl implements EjeInterseccionACalificarMapper{
	
	private final RestriccionEstandarMapper restriccionEstandarMapper;
	
	
	
	public EjeInterseccionACalificarMapperImpl(final RestriccionEstandarMapper restriccionEstandarMapper) {
		super();
		this.restriccionEstandarMapper = restriccionEstandarMapper;
	}

	@Override
	public EjeInterseccionACalificarDTO toDTO(EjeInterseccionACalificarEntity entity) {
		List<RestriccionEstandarDTO> lstRestricciones = new ArrayList<>();
		if (!isNull(entity.getLstRestricciones())) {			
			lstRestricciones = entity.getLstRestricciones()
  									 .stream()
									 .map(restriccionEntity -> restriccionEstandarMapper.toDTO(restriccionEntity))
									 .collect(Collectors.toList());
		}
		
		return new EjeInterseccionACalificarDTO(entity.getIdCalificacion(), 
												entity.getIdDetalleCalificacion(), 
												entity.getIdManiobra(), 
												entity.getIdEstandarRequerido(), 
												entity.getCoCodigoEstandarRequerido(), 
												entity.getIdEstandarObtenido(), 
												entity.getCoCodigoEstandarObtenido(), 
												lstRestricciones);
	}

	@Override
	public EjeInterseccionACalificarEntity toEntity(EjeInterseccionACalificarDTO dto) {
		List<RestriccionEstandarEntity> lstRestricciones = new ArrayList<>();
		if (!isNull(dto.getLstRestricciones())) {		
			lstRestricciones = dto.getLstRestricciones()
								  .stream()
								  .map(restriccionDTO -> restriccionEstandarMapper.toEntity(restriccionDTO))
								  .collect(Collectors.toList());
		}
		
		return new EjeInterseccionACalificarEntity(dto.getIdCalificacion(), 
												   dto.getIdDetalleCalificacion(), 
												   dto.getIdManiobra(), 
												   dto.getIdEstandarRequerido(), 
												   dto.getCoCodigoEstandarRequerido(), 
												   dto.getIdEstandarObtenido(), 
												   dto.getCoCodigoEstandarObtenido(), 
												   lstRestricciones);
	}

	@Override
	public List<EjeInterseccionACalificarDTO> toListDTO(List<EjeInterseccionACalificarEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<EjeInterseccionACalificarEntity> toListEntity(List<EjeInterseccionACalificarDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
