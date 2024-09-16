package pe.mil.fap.model.helpers; 
import java.util.ArrayList;
import java.util.List;

public class DataTableDTO {

	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private int iDisplayRecords;
	private List<?> aaData;

	public DataTableDTO() {
		this.iTotalRecords = 0;
		this.iTotalDisplayRecords = 0;
		this.iDisplayRecords = 0;
		this.aaData = new ArrayList<>();
	}

	public DataTableDTO(int iTotalRecords, int iTotalDisplayRecords, int iDisplayRecords, List<?> aaData) {
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.iDisplayRecords = iDisplayRecords;
		this.aaData = aaData;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public int getiDisplayRecords() {
		return iDisplayRecords;
	}

	public void setiDisplayRecords(int iDisplayRecords) {
		this.iDisplayRecords = iDisplayRecords;
	}

	public List<?> getAaData() {
		return aaData;
	}

	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}

	public DataTableDTO(DataTableDTOBuilder builder) {
		this.iTotalRecords = builder.iTotalRecords;
		this.iTotalDisplayRecords = builder.iTotalDisplayRecords;
		this.iDisplayRecords = builder.iDisplayRecords;
		this.aaData = builder.aaData;
	}

	public static DataTableDTOBuilder builder() {
		return new DataTableDTOBuilder();
	}

	public static class DataTableDTOBuilder {

		private int iTotalRecords;
		private int iTotalDisplayRecords;
		private int iDisplayRecords;
		private List<?> aaData;

		public DataTableDTOBuilder() {
		}

		public DataTableDTOBuilder(int iTotalRecords, int iTotalDisplayRecords, int iDisplayRecords, List<?> aaData) {
			this.iTotalRecords = iTotalRecords;
			this.iTotalDisplayRecords = iTotalDisplayRecords;
			this.iDisplayRecords = iDisplayRecords;
			this.aaData = aaData;
		}

		public DataTableDTOBuilder iTotalRecords(int iTotalRecords) {
			this.iTotalRecords = iTotalRecords;
			return this;
		}

		public DataTableDTOBuilder iTotalDisplayRecords(int iTotalDisplayRecords) {
			this.iTotalDisplayRecords = iTotalDisplayRecords;
			return this;
		}

		public DataTableDTOBuilder iDisplayRecords(int iDisplayRecords) {
			this.iDisplayRecords = iDisplayRecords;
			return this;
		}

		public DataTableDTOBuilder aaData(List<?> aeronaveDTOList) {
			this.aaData = aeronaveDTOList;
			return this;
		}

		public DataTableDTO build() {
			return new DataTableDTO(iTotalRecords, iTotalDisplayRecords, iDisplayRecords, aaData);
		}

	}

}
