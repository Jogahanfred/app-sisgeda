package pe.mil.fap.mappers.administration.inf;

import java.util.List;
 
import pe.mil.fap.entity.administration.BancoManiobraEntity;
import pe.mil.fap.model.administration.BancoManiobraDTO;

public interface BancoManiobraMapper {

	BancoManiobraDTO toDTO(BancoManiobraEntity entity);
	BancoManiobraEntity toEntity(BancoManiobraDTO dto);

    List<BancoManiobraDTO> toListDTO(List<BancoManiobraEntity> listEntity);
    List<BancoManiobraEntity> toListEntity(List<BancoManiobraDTO> listDto);
}
