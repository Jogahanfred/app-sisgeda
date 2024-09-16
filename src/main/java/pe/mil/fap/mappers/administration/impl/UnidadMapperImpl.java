package pe.mil.fap.mappers.administration.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.UnidadEntity;
import pe.mil.fap.mappers.administration.inf.UnidadMapper;
import pe.mil.fap.model.administration.UnidadDTO;

@Component
public class UnidadMapperImpl implements UnidadMapper{

	@Override
	public UnidadDTO toDTO(UnidadEntity entity) {
		return new UnidadDTO(entity.getIdUnidad(), entity.getNuCodigo(), entity.getNoNombre(), entity.getTxDescripcion(), entity.getNuNivel(), entity.getNuCodigoRector(), entity.getFlBloqueado(), entity.getFlEstado(), entity.getTxRutaLogo(), entity.getTxUbicacion(), entity.getTxInformacion(), entity.getTxDescripcionOrganoRector());
	}

	@Override
	public UnidadEntity toEntity(UnidadDTO dto) {
		return new UnidadEntity(dto.getIdUnidad(), dto.getNuCodigo(), dto.getNoNombre(), dto.getTxDescripcion(), dto.getNuNivel(), dto.getNuCodigoRector(), dto.getFlBloqueado(), dto.getFlEstado(), dto.getTxRutaLogo(), dto.getTxUbicacion(), dto.getTxInformacion(), dto.getTxDescripcionOrganoRector());
	}

	@Override
	public List<UnidadDTO> toListDTO(List<UnidadEntity> listEntity) {
		return listEntity.stream()
				 .map(this::toDTO)
		         .collect(Collectors.toList());
	}

	@Override
	public List<UnidadEntity> toListEntity(List<UnidadDTO> listDto) {
		  return listDto.stream()
	              .map(this::toEntity)
	              .collect(Collectors.toList());
	}

}
