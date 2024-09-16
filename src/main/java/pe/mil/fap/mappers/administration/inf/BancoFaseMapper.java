package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.BancoFaseEntity;
import pe.mil.fap.model.administration.BancoFaseDTO;

public interface BancoFaseMapper {

	BancoFaseDTO toDTO(BancoFaseEntity entity);
	BancoFaseEntity toEntity(BancoFaseDTO dto);

    List<BancoFaseDTO> toListDTO(List<BancoFaseEntity> listEntity);
    List<BancoFaseEntity> toListEntity(List<BancoFaseDTO> listDto);
}
