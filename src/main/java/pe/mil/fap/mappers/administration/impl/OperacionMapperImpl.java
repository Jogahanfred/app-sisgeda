package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.OperacionEntity;
import pe.mil.fap.mappers.administration.inf.OperacionMapper;
import pe.mil.fap.model.administration.OperacionDTO;

@Component
public class OperacionMapperImpl implements OperacionMapper {

	@Override
	public OperacionDTO toDTO(OperacionEntity entity) {
		return new OperacionDTO(entity.getIdOperacion(), entity.getIdEscuadron(), entity.getNoNombre(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxDescripcionEscuadron());

	}

	@Override
	public OperacionEntity toEntity(OperacionDTO dto) {
		return new OperacionEntity(dto.getIdOperacion(), dto.getIdEscuadron(), dto.getNoNombre(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxDescripcionEscuadron());

	}

	@Override
	public List<OperacionDTO> toListDTO(List<OperacionEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<OperacionEntity> toListEntity(List<OperacionDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
