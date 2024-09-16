package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.mappers.bussiness.inf.DetalleCalificacionMapper;
import pe.mil.fap.model.bussiness.DetalleCalificacionDTO;

@Component
public class DetalleCalificacionMapperImpl implements DetalleCalificacionMapper {
	
	@Override
	public DetalleCalificacionDTO toDTO(DetalleCalificacionEntity entity) {
		DetalleCalificacionDTO dto = new DetalleCalificacionDTO();
		dto.setIdDetalleCalificacion(entity.getIdDetalleCalificacion());
		dto.setIdCalificacion(entity.getIdCalificacion());
		dto.setIdManiobra(entity.getIdManiobra());
		dto.setIdEstandarObtenido(entity.getIdEstandarObtenido()); 
		return dto;
	}

	@Override
	public DetalleCalificacionEntity toEntity(DetalleCalificacionDTO dto) {
		DetalleCalificacionEntity entity = new DetalleCalificacionEntity();
		entity.setIdDetalleCalificacion(dto.getIdDetalleCalificacion());
		entity.setIdCalificacion(dto.getIdCalificacion());
		entity.setIdManiobra(dto.getIdManiobra());
		entity.setIdEstandarObtenido(dto.getIdEstandarObtenido()); 
		return entity;
	}

	@Override
	public List<DetalleCalificacionDTO> toDTOList(List<DetalleCalificacionEntity> entityList) {
	    return entityList.stream()
	            .map(this::toDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public List<DetalleCalificacionEntity> toEntityList(List<DetalleCalificacionDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}

}
