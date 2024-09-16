package pe.mil.fap.model.helpers;

import pe.mil.fap.common.utils.UtilHelpers;

public class SelectItemDTO {

	private Object value;
	private Object text;
	private Boolean selected;

	private Object order;
	
	public SelectItemDTO(Object value, Object text) {
		this.value = value;
		this.text = text; 
	}

	public SelectItemDTO(Object value, Object text, Boolean selected) {
		this.value = value;
		this.text = text;
		this.selected = selected;
	}

	public SelectItemDTO(Object value, Object text, Boolean selected, Object order) {
		super();
		this.value = value;
		this.text = text;
		this.selected = selected;
		this.order = order;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Object getOrder() {
		return order;
	}

	public void setOrder(Object order) {
		this.order = order;
	}

	public static SelectItemDTO createDefaultItem(String value) {
		return new SelectItemDTO("", /*"--- Seleccione " + */UtilHelpers.capitalize(value)/* + " ---"*/, true);
	}
}
