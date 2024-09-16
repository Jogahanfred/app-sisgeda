package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.mappers.bussiness.inf.DetalleMisionMapper;
import pe.mil.fap.model.bussiness.DetalleMisionDTO;

@Component
public class DetalleMisionMapperImpl implements DetalleMisionMapper {
	
	@Override
	public DetalleMisionDTO toDTO(DetalleMisionEntity entity) {
		DetalleMisionDTO dto = new DetalleMisionDTO();
		dto.setIdDetalleMision(entity.getIdDetalleMision());
		dto.setIdMision(entity.getIdMision());
		dto.setIdManiobra(entity.getIdManiobra());
		dto.setIdEstandarRequerido(entity.getIdEstandarRequerido());
		dto.setNuOrden(entity.getNuOrden());
		dto.setFlBloqueado(entity.getFlBloqueado());
		dto.setFlEstado(entity.getFlEstado());
		return dto;
	}

	@Override
	public DetalleMisionEntity toEntity(DetalleMisionDTO dto) {
		DetalleMisionEntity entity = new DetalleMisionEntity();
		entity.setIdDetalleMision(dto.getIdDetalleMision());
		entity.setIdMision(dto.getIdMision());
		entity.setIdManiobra(dto.getIdManiobra());
		entity.setIdEstandarRequerido(dto.getIdEstandarRequerido());
		entity.setNuOrden(dto.getNuOrden());
		entity.setFlBloqueado(dto.getFlBloqueado());
		entity.setFlEstado(dto.getFlEstado());
		return entity;
	}

	@Override
	public List<DetalleMisionDTO> toDTOList(List<DetalleMisionEntity> entityList) {
	    return entityList.stream()
	            .map(this::toDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public List<DetalleMisionEntity> toEntityList(List<DetalleMisionDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}

}
