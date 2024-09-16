package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.TipoMisionEntity;
import pe.mil.fap.mappers.administration.inf.TipoMisionMapper;
import pe.mil.fap.model.administration.TipoMisionDTO;

@Component
public class TipoMisionMapperImpl implements TipoMisionMapper {

	@Override
	public TipoMisionDTO toDTO(TipoMisionEntity entity) {
		return new TipoMisionDTO(entity.getIdTipoMision(), entity.getCoCodigo(), entity.getNoNombre(), entity.getTxDescripcion(), entity.getFlEstado());

	}

	@Override
	public TipoMisionEntity toEntity(TipoMisionDTO dto) {
		return new TipoMisionEntity(dto.getIdTipoMision(), dto.getCoCodigo(), dto.getNoNombre(), dto.getTxDescripcion(), dto.getFlEstado());

	}

	@Override
	public List<TipoMisionDTO> toListDTO(List<TipoMisionEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<TipoMisionEntity> toListEntity(List<TipoMisionDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
