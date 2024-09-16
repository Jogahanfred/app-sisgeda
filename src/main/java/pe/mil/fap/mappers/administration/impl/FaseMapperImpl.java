package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.FaseEntity;
import pe.mil.fap.mappers.administration.inf.FaseMapper;
import pe.mil.fap.model.administration.FaseDTO;

@Component
public class FaseMapperImpl implements FaseMapper {

	@Override
	public FaseDTO toDTO(FaseEntity entity) {
		return new FaseDTO(entity.getIdFase(), entity.getIdBancoFase(), entity.getIdPrograma(), entity.getNuTotalHora(), entity.getNuTotalSubFase(), entity.getTxFinalidad(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxDescripcionFase(), entity.getTxDescripcionPrograma(), entity.getIdEscuadron());

	}

	@Override
	public FaseEntity toEntity(FaseDTO dto) {
		return new FaseEntity(dto.getIdFase(), dto.getIdBancoFase(), dto.getIdPrograma(), dto.getNuTotalHora(), dto.getNuTotalSubFase(), dto.getTxFinalidad(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxDescripcionFase(), dto.getTxDescripcionPrograma(), dto.getIdEscuadron());

	}

	@Override
	public List<FaseDTO> toListDTO(List<FaseEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<FaseEntity> toListEntity(List<FaseDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
