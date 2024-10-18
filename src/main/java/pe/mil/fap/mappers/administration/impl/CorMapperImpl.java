package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.CorEntity;
import pe.mil.fap.mappers.administration.inf.CorMapper;
import pe.mil.fap.model.administration.CorDTO;

@Component
public class CorMapperImpl implements CorMapper {

	@Override
	public CorDTO toDTO(CorEntity entity) {
		return new CorDTO(entity.getIdCor(), entity.getIdDetalleCalificacion(), entity.getTxCausa(), entity.getTxObservacion(), entity.getTxRecomendacion(), entity.getFeRegistro(), entity.getFeActualizacion());

	}

	@Override
	public CorEntity toEntity(CorDTO dto) {
		return new CorEntity(dto.getIdCor(), dto.getIdDetalleCalificacion(), dto.getTxCausa(), dto.getTxObservacion(), dto.getTxRecomendacion(), dto.getFeRegistro(), dto.getFeActualizacion());

	}

	@Override
	public List<CorDTO> toListDTO(List<CorEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<CorEntity> toListEntity(List<CorDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
