package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.BancoFaseEntity;
import pe.mil.fap.mappers.administration.inf.BancoFaseMapper;
import pe.mil.fap.model.administration.BancoFaseDTO;

@Component
public class BancoFaseMapperImpl implements BancoFaseMapper {

	@Override
	public BancoFaseDTO toDTO(BancoFaseEntity entity) {
		return new BancoFaseDTO(entity.getIdBancoFase(), entity.getNoNombre(), entity.getFlEstado());

	}

	@Override
	public BancoFaseEntity toEntity(BancoFaseDTO dto) {
		return new BancoFaseEntity(dto.getIdBancoFase(), dto.getNoNombre(), dto.getFlEstado());

	}

	@Override
	public List<BancoFaseDTO> toListDTO(List<BancoFaseEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<BancoFaseEntity> toListEntity(List<BancoFaseDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
