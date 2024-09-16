package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.RestriccionEstandarEntity;
import pe.mil.fap.mappers.administration.inf.RestriccionEstandarMapper; 
import pe.mil.fap.model.administration.RestriccionEstandarDTO;

@Component
public class RestriccionEstandarMapperImpl implements RestriccionEstandarMapper{

	@Override
	public RestriccionEstandarDTO toDTO(RestriccionEstandarEntity entity) {
		return new RestriccionEstandarDTO(entity.getIdRestriccionEstandar(), entity.getIdDetalleMision(), entity.getIdEstandar(), entity.getTxMensaje(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxDescripcionEstandar(), entity.getNuNivelEstandar());
	}

	@Override
	public RestriccionEstandarEntity toEntity(RestriccionEstandarDTO dto) {
		return new RestriccionEstandarEntity(dto.getIdRestriccionEstandar(), dto.getIdDetalleMision(), dto.getIdEstandar(), dto.getTxMensaje(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxDescripcionEstandar(), dto.getNuNivelEstandar());
	}

	@Override
	public List<RestriccionEstandarDTO> toListDTO(List<RestriccionEstandarEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<RestriccionEstandarEntity> toListEntity(List<RestriccionEstandarDTO> listDto) {
		return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
