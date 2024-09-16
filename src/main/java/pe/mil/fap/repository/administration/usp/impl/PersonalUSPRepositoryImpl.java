package pe.mil.fap.repository.administration.usp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery; 
import pe.mil.fap.entity.administration.PersonalEntity;
import pe.mil.fap.model.helpers.DataTableDTO;
import pe.mil.fap.model.helpers.ParametroDataTableDTO;
import pe.mil.fap.repository.administration.usp.inf.PersonalUSPRepository;
import pe.mil.fap.repository.exception.RepositoryException;

@Repository
public class PersonalUSPRepositoryImpl implements PersonalUSPRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public DataTableDTO paginar(ParametroDataTableDTO parametro) throws RepositoryException {
		try { 
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("personal.paginar");
			spq.setParameter("P_PAGE", parametro.getInicio());
			spq.setParameter("P_SIZE", parametro.getTamanio());
			spq.setParameter("P_FILTER", parametro.getFiltro());
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<PersonalEntity> lstPersonal = new ArrayList<>();
			Integer totalRegistros = 0;
			
			for (Object[] obj : results) {
				PersonalEntity personal = new PersonalEntity();
				totalRegistros = ((Number)obj[0]).intValue(); 
				personal.setOrden(Integer.parseInt(String.valueOf(obj[1])));
				personal.setPlana(Integer.parseInt(String.valueOf(obj[2])));
				personal.setNsa(String.valueOf(obj[3]));
				personal.setGrado(String.valueOf(obj[4]));
				personal.setDatos(String.valueOf(obj[5]));
				personal.setUnidadOrigen((String) obj[6]);
				personal.setUnidadParte(String.valueOf(obj[7]));
				personal.setDni(String.valueOf(obj[8]));
				personal.setEspecialidad((String) obj[9]);
				personal.setSexo((String) obj[10]);
				personal.setNacimiento(String.valueOf(obj[11]));
				personal.setTipoSangre(String.valueOf(obj[12])); 
				personal.setFotografia(String.valueOf(obj[13]));
				lstPersonal.add(personal);				
			}
			return DataTableDTO.builder()
		                       .iTotalRecords(totalRegistros) 
		                       .iTotalDisplayRecords(totalRegistros) 
			                   .iDisplayRecords(lstPersonal.size()) 
		                       .aaData(lstPersonal)
		                       .build();
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalEntity> listar() throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("personal.listar");
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<PersonalEntity> lstPersonal = new ArrayList<>();

			for (Object[] obj : results) {
				PersonalEntity personal = new PersonalEntity();
				
				personal.setOrden(Integer.parseInt(String.valueOf(obj[0])));
				personal.setPlana(Integer.parseInt(String.valueOf(obj[1])));
				personal.setNsa(String.valueOf(obj[2]));
				personal.setGrado(String.valueOf(obj[3]));
				personal.setDatos(String.valueOf(obj[4]));
				personal.setUnidadOrigen((String) obj[5]);
				personal.setUnidadParte(String.valueOf(obj[6]));
				personal.setDni(String.valueOf(obj[7]));
				personal.setEspecialidad((String) obj[8]);
				personal.setSexo((String) obj[9]);
				personal.setNacimiento(String.valueOf(obj[10]));
				personal.setTipoSangre(String.valueOf(obj[11])); 
				personal.setFotografia(String.valueOf(obj[12])); 
				lstPersonal.add(personal);

			}
			return lstPersonal;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<PersonalEntity> buscarPorNsa(String nsa) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("personal.buscarPorNsa");

			spq.setParameter("P_NSA", nsa);
			spq.execute();

			List<Object[]> results = spq.getResultList();
			List<PersonalEntity> lstPersonal = new ArrayList<>();

			for (Object[] obj : results) {
				PersonalEntity personal = new PersonalEntity();
				
				personal.setOrden(Integer.parseInt(String.valueOf(obj[0])));
				personal.setPlana(Integer.parseInt(String.valueOf(obj[1])));
				personal.setNsa(String.valueOf(obj[2]));
				personal.setGrado(String.valueOf(obj[3]));
				personal.setDatos(String.valueOf(obj[4]));
				personal.setUnidadOrigen((String) obj[5]);
				personal.setUnidadParte(String.valueOf(obj[6]));
				personal.setDni(String.valueOf(obj[7]));
				personal.setEspecialidad((String) obj[8]);
				personal.setSexo((String) obj[9]);
				personal.setNacimiento(String.valueOf(obj[10]));
				personal.setTipoSangre(String.valueOf(obj[11])); 
				personal.setFotografia(String.valueOf(obj[12])); 
				lstPersonal.add(personal);

			}
			if (lstPersonal.isEmpty()) { 
				return Optional.empty();
			}
			PersonalEntity personal = (PersonalEntity) lstPersonal.get(0);
			return Optional.of(personal);
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	
	@Override
	public String actualizarFotografia(PersonalEntity entity) throws RepositoryException {
		try {
			StoredProcedureQuery spq = entityManager.createNamedStoredProcedureQuery("personal.actualizarFotografia");

			spq.setParameter("P_NSA", entity.getNsa());
			spq.setParameter("P_FOTOGRAFIA", entity.getFotografia()); 

			spq.execute();

			String mensaje = (String) spq.getOutputParameterValue("P_MENSAJE"); 
			return mensaje; 
		} catch (Exception e) {
			throw new RepositoryException(e);
		}
	}
}
