package pe.mil.fap.mappers.helpers.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.helpers.EjeXEntity;
import pe.mil.fap.mappers.helpers.inf.EjeXMapper;
import pe.mil.fap.model.helpers.EjeXDTO;
 
@Component
public class EjeXMapperImpl implements EjeXMapper{

	@Override
	public EjeXDTO toDTO(EjeXEntity entity) {
		return new EjeXDTO(entity.getIdMision(), entity.getCoCodigo(), entity.getIdTipoMision(), entity.getCoCodigoTipoMision(), entity.getNoNombreTipoMision(), entity.getIdCalificacion(), entity.getIdCalificador());
	}

	@Override
	public EjeXEntity toEntity(EjeXDTO dto) {
		return new EjeXEntity(dto.getIdMision(), dto.getCoCodigo(), dto.getIdTipoMision(), dto.getCoCodigoTipoMision(), dto.getNoNombreTipoMision(), dto.getIdCalificacion(), dto.getIdCalificador());
	}

	@Override
	public List<EjeXDTO> toListDTO(List<EjeXEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<EjeXEntity> toListEntity(List<EjeXDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
