package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.BancoSubFaseEntity;
import pe.mil.fap.model.administration.BancoSubFaseDTO;

public interface BancoSubFaseMapper {

	BancoSubFaseDTO toDTO(BancoSubFaseEntity entity);
	BancoSubFaseEntity toEntity(BancoSubFaseDTO dto);

    List<BancoSubFaseDTO> toListDTO(List<BancoSubFaseEntity> listEntity);
    List<BancoSubFaseEntity> toListEntity(List<BancoSubFaseDTO> listDto);
}
