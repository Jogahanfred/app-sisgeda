package pe.mil.fap.repository.administration.usp.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import pe.mil.fap.entity.administration.EscuadronEntity;
import pe.mil.fap.entity.administration.EstandarEntity; 
import pe.mil.fap.repository.administration.usp.inf.EstandarUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class EstandarUSPRepositoryImpl implements EstandarUSPRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<EstandarEntity> listarEstandares() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("estandar.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EstandarEntity> lstEstandares = new ArrayList<>();

			for (Object[] obj : results) {
				EstandarEntity estandar = new EstandarEntity();

				estandar.setIdEstandar(Integer.parseInt(String.valueOf(obj[0]))); 
				estandar.setCoCodigo((String) obj[1]); 
				estandar.setNoNombre((String) obj[2]); 
				estandar.setTxDescripcion((String) obj[3]); 
				estandar.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				estandar.setNuNivel(Integer.parseInt(String.valueOf(obj[5])));
				lstEstandares.add(estandar);

			}
			return lstEstandares;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<EstandarEntity> buscarPorCodigo(String coCodigo) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("estandar.buscarPorCodigo");
			spq.setParameter("P_CODIGO", coCodigo); 
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<EstandarEntity> lstEstandares = new ArrayList<>();

			for (Object[] obj : results) {
				EstandarEntity estandar = new EstandarEntity();

				estandar.setIdEstandar(Integer.parseInt(String.valueOf(obj[0]))); 
				estandar.setCoCodigo((String) obj[1]); 
				estandar.setNoNombre((String) obj[2]); 
				estandar.setTxDescripcion((String) obj[3]); 
				estandar.setFlEstado(Integer.parseInt(String.valueOf(obj[4])));
				estandar.setNuNivel(Integer.parseInt(String.valueOf(obj[5])));
				lstEstandares.add(estandar);

			} 
			
			if (lstEstandares.isEmpty()) { 
				return Optional.empty();
			}
			EstandarEntity estandar = (EstandarEntity) lstEstandares.get(0);
			return Optional.of(estandar);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	} 
}
