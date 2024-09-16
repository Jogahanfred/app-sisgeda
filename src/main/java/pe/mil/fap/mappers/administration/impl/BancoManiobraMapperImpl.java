package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.BancoManiobraEntity;
import pe.mil.fap.mappers.administration.inf.BancoManiobraMapper;
import pe.mil.fap.model.administration.BancoManiobraDTO;

@Component
public class BancoManiobraMapperImpl implements BancoManiobraMapper {

	@Override
	public BancoManiobraDTO toDTO(BancoManiobraEntity entity) {
		return new BancoManiobraDTO(entity.getIdBancoManiobra(), entity.getNoNombre(), entity.getFlEstado());

	}

	@Override
	public BancoManiobraEntity toEntity(BancoManiobraDTO dto) {
		return new BancoManiobraEntity(dto.getIdBancoManiobra(), dto.getNoNombre(), dto.getFlEstado());

	}

	@Override
	public List<BancoManiobraDTO> toListDTO(List<BancoManiobraEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<BancoManiobraEntity> toListEntity(List<BancoManiobraDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
