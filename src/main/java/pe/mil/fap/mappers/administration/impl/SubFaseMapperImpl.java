package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.SubFaseEntity;
import pe.mil.fap.mappers.administration.inf.SubFaseMapper;
import pe.mil.fap.model.administration.SubFaseDTO;

@Component
public class SubFaseMapperImpl implements SubFaseMapper {

	@Override
	public SubFaseDTO toDTO(SubFaseEntity entity) {
		return new SubFaseDTO(entity.getIdSubFase(), 
							  entity.getIdBancoSubFase(), 
							  entity.getIdFase(),
							  entity.getNuTotalHora(), 
							  entity.getNuTotalMision(),
							  entity.getNuTotalManiobra(),
							  entity.getCoCodigo(), 
							  entity.getTxFinalidad(), 
							  entity.getFlBloqueado(), 
							  entity.getFlEstado(), 
							  entity.getTxDescripcionSubFase(), 
							  entity.getTxDescripcionFase(), 
							  entity.getIdPrograma(), 
							  entity.getTxDescripcionPrograma(), 
							  entity.getIdEscuadron(),
							  entity.getIdBancoFase());

	}

	@Override
	public SubFaseEntity toEntity(SubFaseDTO dto) {
		return new SubFaseEntity(dto.getIdSubFase(), 
								 dto.getIdBancoSubFase(), 
								 dto.getIdFase(), 
								 dto.getNuTotalHora(), 
								 dto.getNuTotalMision(),
								 dto.getNuTotalManiobra(),
								 dto.getCoCodigo(), 
								 dto.getTxFinalidad(), 
								 dto.getFlBloqueado(), 
								 dto.getFlEstado(), 
								 dto.getTxDescripcionSubFase(), 
								 dto.getTxDescripcionFase(), 
								 dto.getIdPrograma(), 
								 dto.getTxDescripcionPrograma(), 
								 dto.getIdEscuadron(),
								 dto.getIdBancoFase());

	}

	@Override
	public List<SubFaseDTO> toListDTO(List<SubFaseEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<SubFaseEntity> toListEntity(List<SubFaseDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
