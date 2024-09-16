package pe.mil.fap.model.helpers;

public class MessageDTO {

	private String type;
	private String message;
	private Object data;

	public MessageDTO() {
	}

	public MessageDTO(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public MessageDTO(MessageDTOBuilder builder) {

		this.type = builder.type;
		this.message = builder.message;
		this.data = builder.data;
	}

	public static MessageDTOBuilder builder() {
		return new MessageDTOBuilder();
	}

	public static class MessageDTOBuilder {

		private String type;
		private String message;
		private Object data;

		public MessageDTOBuilder() {
		}

		public MessageDTOBuilder(String type, String message, Object data) {
			super();
			this.type = type;
			this.message = message;
			this.data = data;
		}

		public MessageDTOBuilder type(String type) {
			this.type = type;
			return this;
		}

		public MessageDTOBuilder message(String message) {
			int iSeparator = message.indexOf("|");
			if (iSeparator != -1) {
				this.type = message.substring(0, iSeparator);
				this.message = message.substring(iSeparator + 1);
			} else {
				this.message = message;
			}
			return this;
		}

		public MessageDTOBuilder data(Object data) {
			this.data = data;
			return this;
		}

		public MessageDTO build() {
			return new MessageDTO(this);
		}

	}

}