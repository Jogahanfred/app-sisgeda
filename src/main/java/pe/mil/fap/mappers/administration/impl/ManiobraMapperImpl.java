package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.ManiobraEntity;
import pe.mil.fap.mappers.administration.inf.ManiobraMapper;
import pe.mil.fap.model.administration.ManiobraDTO;

@Component
public class ManiobraMapperImpl implements ManiobraMapper {

	@Override
	public ManiobraDTO toDTO(ManiobraEntity entity) {
		return new ManiobraDTO(entity.getIdManiobra(), entity.getIdBancoManiobra(), entity.getIdOperacion(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxDescripcionOperacion(), entity.getTxDescripcionBancoManiobra(), entity.getIdEscuadron(), entity.getTxDescripcionEscuadron());

	}

	@Override
	public ManiobraEntity toEntity(ManiobraDTO dto) {
		return new ManiobraEntity(dto.getIdManiobra(), dto.getIdBancoManiobra(), dto.getIdOperacion(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxDescripcionOperacion(), dto.getTxDescripcionBancoManiobra(), dto.getIdEscuadron(), dto.getTxDescripcionEscuadron());

	}

	@Override
	public List<ManiobraDTO> toListDTO(List<ManiobraEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<ManiobraEntity> toListEntity(List<ManiobraDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
