package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.ProgramaEntity;
import pe.mil.fap.mappers.administration.inf.ProgramaMapper;
import pe.mil.fap.model.administration.ProgramaDTO;

@Component
public class ProgramaMapperImpl implements ProgramaMapper {

	@Override
	public ProgramaDTO toDTO(ProgramaEntity entity) {
		return new ProgramaDTO(entity.getIdPrograma(), entity.getIdEscuadron(), entity.getNuPeriodo(), entity.getNoTipoInstruccion(), entity.getNoNombre(), entity.getTxDescripcion(), entity.getTxFinalidad(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxDescripcionEscuadron(), entity.getIdUnidad());
 	}

	@Override
	public ProgramaEntity toEntity(ProgramaDTO dto) {
		return new ProgramaEntity(dto.getIdPrograma(), dto.getIdEscuadron(), dto.getNuPeriodo(), dto.getNoTipoInstruccion(), dto.getNoNombre(), dto.getTxDescripcion(), dto.getTxFinalidad(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxDescripcionEscuadron(), dto.getIdUnidad());
	}

	@Override
	public List<ProgramaDTO> toListDTO(List<ProgramaEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<ProgramaEntity> toListEntity(List<ProgramaDTO> listDto) {
	    return listDto.stream()
		              .map(this::toEntity)
		              .collect(Collectors.toList());
	}

}
