package mainPackage;

import org.springframework.http.HttpStatus;

public class FormatReturnMessage {
	private String text;
	private HttpStatus response;

	public FormatReturnMessage(String text, HttpStatus response) {
		super();
		this.text = text;
		this.response = response;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public HttpStatus getResponse() {
		return response;
	}
	
	public void setResponse(HttpStatus response) {
		this.response = response;
	}
	
	
}
