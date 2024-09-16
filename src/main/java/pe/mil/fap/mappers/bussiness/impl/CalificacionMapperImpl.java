package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.bussiness.DetalleCalificacionEntity;
import pe.mil.fap.entity.bussiness.CalificacionEntity;
import pe.mil.fap.mappers.bussiness.inf.DetalleCalificacionMapper;
import pe.mil.fap.mappers.bussiness.inf.CalificacionMapper;
import pe.mil.fap.model.bussiness.DetalleCalificacionDTO;
import pe.mil.fap.model.bussiness.CalificacionDTO;

@Component
public class CalificacionMapperImpl implements CalificacionMapper{
	
	private final DetalleCalificacionMapper detalleCalificacionMapper;
	
	public CalificacionMapperImpl(DetalleCalificacionMapper detalleCalificacionMapper) { 
		this.detalleCalificacionMapper = detalleCalificacionMapper;
	}

	@Override
	public CalificacionDTO toDTO(CalificacionEntity entity) {
		List<DetalleCalificacionDTO> detalleCalificacionesDTO = entity.getLstDetalleCalificacion()
														  .stream()
														  .map(detalleCalificacionEntity -> detalleCalificacionMapper.toDTO(detalleCalificacionEntity))
														  .collect(Collectors.toList()); 
		return new CalificacionDTO(entity.getIdCalificacion(), 
								   entity.getIdMision(), 
								   entity.getIdAeronave(), 
								   entity.getIdCalificado(), 
								   entity.getIdCalificador(), 
								   entity.getQtNota(), 
								   entity.getCoCalificacionVuelo(), 
								   entity.getFeProgramacion(), 
								   entity.getFeEjecucion(), 
								   entity.getFlActualizacion(), 
								   entity.getTxObservacion(), 
								   entity.getTxRecomendacion(), 
								   detalleCalificacionesDTO);
	
	}

	@Override
	public CalificacionEntity toEntity(CalificacionDTO dto) {
		List<DetalleCalificacionEntity> detalleCalificacionesEntity = dto.getLstDetalleCalificacion()
																  .stream()
																  .map(detalleCalificacionDTO -> detalleCalificacionMapper.toEntity(detalleCalificacionDTO))
																  .collect(Collectors.toList()); 
		return new CalificacionEntity(dto.getIdCalificacion(), 
									  dto.getIdMision(), 
									  dto.getIdAeronave(), 
									  dto.getIdCalificado(), 
									  dto.getIdCalificador(), 
									  dto.getQtNota(), 
									  dto.getCoCalificacionVuelo(), 
									  dto.getFeProgramacion(), 
									  dto.getFeEjecucion(), 
									  dto.getFlActualizacion(), 
									  dto.getTxObservacion(), 
									  dto.getTxRecomendacion(), 
									  detalleCalificacionesEntity);
	}

	@Override
	public List<CalificacionEntity> toEntityList(List<CalificacionDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}


}
