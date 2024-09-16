package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.AeronaveEntity;
import pe.mil.fap.mappers.administration.inf.AeronaveMapper;
import pe.mil.fap.model.administration.AeronaveDTO;

@Component
public class AeronaveMapperImpl implements AeronaveMapper {

	@Override
	public AeronaveDTO toDTO(AeronaveEntity entity) {
		return new AeronaveDTO(entity.getIdAeronave(), entity.getIdUnidad(), entity.getIdFlota(), entity.getCoNroCola(), entity.getFlEstado(), entity.getTxRutaImagen(), entity.getTxDescripcionUnidad(), entity.getTxDescripcionFlota());

	}

	@Override
	public AeronaveEntity toEntity(AeronaveDTO dto) {
		return new AeronaveEntity(dto.getIdAeronave(), dto.getIdUnidad(), dto.getIdFlota(), dto.getCoNroCola(), dto.getFlEstado(), dto.getTxRutaImagen(), dto.getTxDescripcionUnidad(), dto.getTxDescripcionFlota());

	}

	@Override
	public List<AeronaveDTO> toListDTO(List<AeronaveEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<AeronaveEntity> toListEntity(List<AeronaveDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
