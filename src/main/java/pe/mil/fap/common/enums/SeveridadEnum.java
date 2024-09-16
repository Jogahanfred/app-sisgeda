package pe.mil.fap.common.enums;

public enum SeveridadEnum {

	SUCCESS("success"), INFO("info"), WARNING("warning"), ERROR("error");

	private String valor;
		
	private SeveridadEnum(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}

}