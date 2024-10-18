package pe.mil.fap.common.enums;

public enum EstandarEnum {
	DEMOSTRATIVO("D", 1), 
	INSUFICIENTE("I", 2),
	REGULAR("R", 3),
	BUENO("B", 4),
	EXCELENTE("E", 5),
	SIN_ESTANDAR("SE", 0),
	PELIGROSO("P", 0),
	NO_REGISTRADA("NR", 0);

	private final String codigo;
	private final Integer nivel;

	private EstandarEnum(String codigo, Integer nivel) {
		this.codigo = codigo;
		this.nivel = nivel;
	}

	public Integer getNivel() {
		return nivel;
	}

	public String getCodigo() {
		return codigo;
	}
    public static int getNivelPorCodigo(String codigo) {
        for (EstandarEnum c : values()) {
            if (c.getCodigo().equals(codigo)) {
                return c.getNivel();
            }
        }
        return -1; 
    }
}
