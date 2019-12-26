package it.marco.marco.bean.http;

import org.springframework.http.HttpStatus;

public class Outcome {
	
	private HttpStatus code;
	private String message;
	private Type type;
	private String severity;
	private Widget widget;
	
	public Outcome(HttpStatus code, String message, Type type, String severity, Widget widget) {
		this.code = code;
		this.message = message;
		this.type = type;
		this.widget = widget;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
}
