package pe.mil.fap.common.enums;

public enum GradoEnum {
	ALF("Alferez"), 
	TEN("Teniente"),
	CAP("Capitán"),
	MAY("Mayor"),
	COM("Comandante"),
	COR("Coronel"),
	MAG("Mayor General"),
	TTG("Teniente General"),
	GDA("Comandante General");

	private final String descripcion;

	private GradoEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
