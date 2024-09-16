package pe.mil.fap.mappers.bussiness.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.mil.fap.entity.bussiness.DetalleMisionEntity;
import pe.mil.fap.entity.bussiness.MisionEntity;
import pe.mil.fap.mappers.bussiness.inf.DetalleMisionMapper;
import pe.mil.fap.mappers.bussiness.inf.MisionMapper;
import pe.mil.fap.model.bussiness.DetalleMisionDTO;
import pe.mil.fap.model.bussiness.MisionDTO;

@Component
public class MisionMapperImpl implements MisionMapper{
	
	private final DetalleMisionMapper detalleMisionMapper;
	
	public MisionMapperImpl(DetalleMisionMapper detalleMisionMapper) { 
		this.detalleMisionMapper = detalleMisionMapper;
	}

	@Override
	public MisionDTO toDTO(MisionEntity entity) {
		List<DetalleMisionDTO> detalleMisionesDTO = entity.getLstDetalleMision()
														  .stream()
														  .map(detalleMisionEntity -> detalleMisionMapper.toDTO(detalleMisionEntity))
														  .collect(Collectors.toList()); 
		return new MisionDTO(entity.getIdMision(), 
							 entity.getIdSubFase(), 
							 entity.getIdTipoMision(), 
							 entity.getCoCodigo(), 
							 entity.getFlChequeo(), 
							 entity.getFlBloqueado(), 
							 entity.getFlEstado(), 
							 detalleMisionesDTO);
	}

	@Override
	public MisionEntity toEntity(MisionDTO dto) {
		List<DetalleMisionEntity> detalleMisionesEntity = dto.getLstDetalleMision()
				  .stream()
				  .map(detalleMisionDTO -> detalleMisionMapper.toEntity(detalleMisionDTO))
				  .collect(Collectors.toList()); 
		return new MisionEntity(dto.getIdMision(), 
							    dto.getIdSubFase(), 
								dto.getIdTipoMision(), 
								dto.getCoCodigo(), 
								dto.getFlChequeo(), 
								dto.getFlBloqueado(), 
								dto.getFlEstado(), 
								detalleMisionesEntity);
	}

	@Override
	public List<MisionEntity> toEntityList(List<MisionDTO> dtoList) {
	    return dtoList.stream()
	            .map(this::toEntity)
	            .collect(Collectors.toList());
	}


}
