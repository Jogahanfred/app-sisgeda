package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.MiembroEntity;
import pe.mil.fap.mappers.administration.inf.MiembroMapper;
import pe.mil.fap.model.administration.MiembroDTO;

@Component
public class MiembroMapperImpl implements MiembroMapper {

	@Override
	public MiembroDTO toDTO(MiembroEntity entity) {
		return new MiembroDTO(entity.getIdMiembro(), entity.getIdUnidad(), entity.getNoRol(), entity.getCoNsa(), entity.getNuPeriodo(), entity.getFlBloqueado(), entity.getFlEstado());

	}

	@Override
	public MiembroEntity toEntity(MiembroDTO dto) {
		return new MiembroEntity(dto.getIdMiembro(), dto.getIdUnidad(), dto.getNoRol(), dto.getCoNsa(), dto.getNuPeriodo(), dto.getFlBloqueado(), dto.getFlEstado());

	}

	@Override
	public List<MiembroDTO> toListDTO(List<MiembroEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<MiembroEntity> toListEntity(List<MiembroDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
