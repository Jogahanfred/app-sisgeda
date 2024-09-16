package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.administration.BancoFaseEntity;
import pe.mil.fap.entity.administration.GrupoEntity;
import pe.mil.fap.entity.bussiness.GrupoMiembroEntity;
import pe.mil.fap.mappers.bussiness.inf.GrupoMapper;
import pe.mil.fap.mappers.bussiness.inf.GrupoMiembroMapper;
import pe.mil.fap.model.administration.BancoFaseDTO;
import pe.mil.fap.model.administration.GrupoDTO;
import pe.mil.fap.model.bussiness.GrupoMiembroDTO; 

@Component
public class GrupoMapperImpl implements GrupoMapper{
	
	private final GrupoMiembroMapper grupoMiembroMapper;
	
	public GrupoMapperImpl(GrupoMiembroMapper grupoMiembroMapper) { 
		this.grupoMiembroMapper = grupoMiembroMapper;
	}

	@Override
	public GrupoDTO toDTO(GrupoEntity entity) {
		/*
		List<GrupoMiembroDTO> detalleAlumnoDTO = entity.getLstAlumnos()
														  .stream()
														  .map(grupoMiembroEntity -> grupoMiembroMapper.toDTO(grupoMiembroEntity))
														  .collect(Collectors.toList()); */
		return new GrupoDTO(entity.getIdGrupo(), 
							entity.getIdUnidad(), 
							entity.getCoCodigo(), 
							entity.getNoNombre(), 
							entity.getNoSituacion(), 
							entity.getNuPeriodo()/*, 
							detalleAlumnoDTO*/);
	}

	@Override
	public GrupoEntity toEntity(GrupoDTO dto) {
		/*
		List<GrupoMiembroEntity> detalleAlumnoEntity = dto.getLstAlumnos()
				  .stream()
				  .map(grupoMiembroDTO -> grupoMiembroMapper.toEntity(grupoMiembroDTO))
				  .collect(Collectors.toList()); */
		return new GrupoEntity(dto.getIdGrupo(), 
							   dto.getIdUnidad(), 
							   dto.getCoCodigo(), 
							   dto.getNoNombre(), 
							   dto.getNoSituacion(), 
							   dto.getNuPeriodo()/*, 
							   detalleAlumnoEntity*/);
	}	
	
	@Override
	public List<GrupoDTO> toListDTO(List<GrupoEntity> listEntity) {
		return listEntity.stream()
						 .map(this::toDTO)
				         .collect(Collectors.toList());
	}

	@Override
	public List<GrupoEntity> toEntityList(List<GrupoDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}


}
