package pe.mil.fap.mappers.helpers.impl;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.entity.helpers.EjeInterseccionEntity;
import pe.mil.fap.mappers.administration.inf.RestriccionEstandarMapper;
import pe.mil.fap.mappers.helpers.inf.EjeInterseccionMapper;
import pe.mil.fap.model.administration.RestriccionEstandarDTO;
import pe.mil.fap.model.helpers.EjeInterseccionDTO;
 
@Component
public class EjeInterseccionMapperImpl implements EjeInterseccionMapper{
	
	private final RestriccionEstandarMapper restriccionEstandarMapper;
	
	
	
	public EjeInterseccionMapperImpl(final RestriccionEstandarMapper restriccionEstandarMapper) {
		super();
		this.restriccionEstandarMapper = restriccionEstandarMapper;
	}

	@Override
	public EjeInterseccionDTO toDTO(EjeInterseccionEntity entity) {
		List<RestriccionEstandarDTO> lstRestricciones = new ArrayList<>();
		if (!isNull(entity.getLstRestricciones())) {			
			lstRestricciones = entity.getLstRestricciones()
  									 .stream()
									 .map(restriccionEntity -> restriccionEstandarMapper.toDTO(restriccionEntity))
									 .collect(Collectors.toList());
		}
		
		return new EjeInterseccionDTO(entity.getIdDetalleMision(), 
									  entity.getIdMision(), 
									  entity.getIdManiobra(), 
									  entity.getIdEstandarRequerido(), 
									  entity.getCoCodigoEstandar(),
									  lstRestricciones);
	}

	@Override
	public EjeInterseccionEntity toEntity(EjeInterseccionDTO dto) {
		List<RestriccionEstandarEntity> lstRestricciones = new ArrayList<>();
		if (!isNull(dto.getLstRestricciones())) {		
			lstRestricciones = dto.getLstRestricciones()
								  .stream()
								  .map(restriccionDTO -> restriccionEstandarMapper.toEntity(restriccionDTO))
								  .collect(Collectors.toList());
		}
		
		return new EjeInterseccionEntity(dto.getIdDetalleMision(), 
									     dto.getIdMision(), 
									     dto.getIdManiobra(), 
									     dto.getIdEstandarRequerido(), 
									     dto.getCoCodigoEstandar(),
									     lstRestricciones);
	}

	@Override
	public List<EjeInterseccionDTO> toListDTO(List<EjeInterseccionEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<EjeInterseccionEntity> toListEntity(List<EjeInterseccionDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
