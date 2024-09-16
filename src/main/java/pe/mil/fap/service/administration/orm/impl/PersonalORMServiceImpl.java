package pe.mil.fap.service.administration.orm.impl;

import java.io.FileInputStream; 
       
import org.springframework.stereotype.Service;

import com.aspose.cells.Row;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection; 
import pe.mil.fap.mappers.administration.inf.PersonalMapper;
import pe.mil.fap.model.administration.PersonalDTO;
import pe.mil.fap.repository.administration.orm.PersonalORMRepository;
import pe.mil.fap.service.administration.orm.inf.PersonalORMService;
import pe.mil.fap.service.exception.ServiceException;

@Service
public class PersonalORMServiceImpl implements PersonalORMService {

	private final PersonalORMRepository personalORMRepository;
	private final PersonalMapper personalMapper;

	public PersonalORMServiceImpl(PersonalORMRepository personalORMRepository, PersonalMapper personalMapper) {
		super();
		this.personalORMRepository = personalORMRepository;
		this.personalMapper = personalMapper;
	}
 
	@Override
	public Boolean savePersonalXLSXToDTO() throws ServiceException {
		try {

			FileInputStream archivo = new FileInputStream(
					"D:\\Background - Laptop\\Demo-WEB\\sisgeda\\src\\main\\resources\\document\\Escalafon.xlsx");

			Workbook libro = new Workbook(archivo);

			WorksheetCollection collection = libro.getWorksheets();
			Worksheet hoja = collection.get(0);

			int numero_filas = hoja.getCells().getMaxDataRow();

			for (int i = 1; i <= numero_filas; i++) {
				Row fila = hoja.getCells().getRows().get(i);

				@SuppressWarnings("deprecation")
				PersonalDTO personal = new PersonalDTO(fila.getCellByIndex(0).getIntValue(),
													   fila.getCellByIndex(1).getIntValue(), 
													   fila.getCellByIndex(2).getStringValue(),
													   fila.getCellByIndex(3).getStringValue(), 
													   fila.getCellByIndex(4).getStringValue(),
													   fila.getCellByIndex(5).getStringValue(), 
													   fila.getCellByIndex(6).getStringValue(),
													   fila.getCellByIndex(7).getStringValue(), 
													   fila.getCellByIndex(8).getStringValue(),
													   fila.getCellByIndex(9).getStringValue(), 
													   fila.getCellByIndex(10).getStringValue(),
													   fila.getCellByIndex(11).getStringValue(),
													   null);

				personalORMRepository.save(personalMapper.toEntity(personal));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
