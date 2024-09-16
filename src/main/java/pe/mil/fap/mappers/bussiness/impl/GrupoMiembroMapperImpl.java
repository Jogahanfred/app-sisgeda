package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.mappers.bussiness.inf.GrupoMiembroMapper;
import pe.mil.fap.model.bussiness.GrupoMiembroDTO;

@Component
public class GrupoMiembroMapperImpl implements GrupoMiembroMapper {
	
	@Override
	public GrupoMiembroDTO toDTO(GrupoMiembroEntity entity) {
		GrupoMiembroDTO dto = new GrupoMiembroDTO();
		dto.setIdGrupo(entity.getIdGrupo());
		dto.setIdMiembro(entity.getIdMiembro());
		dto.setNoSituacion(entity.getNoSituacion());
		dto.setFeRegistro(entity.getFeRegistro());
		dto.setFeActualizacion(entity.getFeActualizacion());
	
		return dto;
	}

	@Override
	public GrupoMiembroEntity toEntity(GrupoMiembroDTO dto) {
		GrupoMiembroEntity entity = new GrupoMiembroEntity();
		entity.setIdGrupo(dto.getIdGrupo());
		entity.setIdMiembro(dto.getIdMiembro());
		entity.setNoSituacion(dto.getNoSituacion());
		entity.setFeRegistro(dto.getFeRegistro());
		entity.setFeActualizacion(dto.getFeActualizacion());
	
		return entity;
	}

	@Override
	public List<GrupoMiembroDTO> toDTOList(List<GrupoMiembroEntity> entityList) {
	    return entityList.stream()
	            .map(this::toDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public List<GrupoMiembroEntity> toEntityList(List<GrupoMiembroDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}

}
