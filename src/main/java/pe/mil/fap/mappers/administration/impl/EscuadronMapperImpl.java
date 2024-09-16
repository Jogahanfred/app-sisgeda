package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.mappers.administration.inf.EscuadronMapper;
import pe.mil.fap.model.administration.EscuadronDTO;

@Component
public class EscuadronMapperImpl implements EscuadronMapper{

	@Override
	public EscuadronDTO toDTO(EscuadronEntity entity) { 
		return new EscuadronDTO(entity.getIdEscuadron(), entity.getIdUnidad(), entity.getCoSigla(), entity.getTxDescripcion(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxRutaLogo(), entity.getTxInformacion(), entity.getTxDescripcionUnidad());
	}

	@Override
	public EscuadronEntity toEntity(EscuadronDTO dto) {
		return new EscuadronEntity(dto.getIdEscuadron(), dto.getIdUnidad(), dto.getCoSigla(), dto.getTxDescripcion(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxRutaLogo(), dto.getTxInformacion(), dto.getTxDescripcionUnidad());
	}

	@Override
	public List<EscuadronDTO> toListDTO(List<EscuadronEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<EscuadronEntity> toListEntity(List<EscuadronDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
