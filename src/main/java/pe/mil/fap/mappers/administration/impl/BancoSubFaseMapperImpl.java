package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.BancoSubFaseEntity;
import pe.mil.fap.mappers.administration.inf.BancoSubFaseMapper;
import pe.mil.fap.model.administration.BancoSubFaseDTO;

@Component
public class BancoSubFaseMapperImpl implements BancoSubFaseMapper {

	@Override
	public BancoSubFaseDTO toDTO(BancoSubFaseEntity entity) {
		return new BancoSubFaseDTO(entity.getIdBancoSubFase(), entity.getNoNombre(), entity.getFlEstado());

	}

	@Override
	public BancoSubFaseEntity toEntity(BancoSubFaseDTO dto) {
		return new BancoSubFaseEntity(dto.getIdBancoSubFase(), dto.getNoNombre(), dto.getFlEstado());

	}

	@Override
	public List<BancoSubFaseDTO> toListDTO(List<BancoSubFaseEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<BancoSubFaseEntity> toListEntity(List<BancoSubFaseDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
