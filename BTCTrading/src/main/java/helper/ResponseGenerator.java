package helper;

import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

	private HttpResponse status;
	private String message;

	public ResponseGenerator() {
	}

	public ResponseGenerator(HttpResponse status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpResponse getStatus() {
		return status;
	}

	public void setStatus(HttpResponse status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void appendMessage(String message) {
		this.message += " " + message;
	}

	public ResponseEntity<String> getResponse() {
		// TODO add support for accepted, created, notFound,..
		switch (status) {
		case OK:
			return ResponseEntity.ok().body(message);
		case BAD_REQUEST:
			return ResponseEntity.badRequest().body(message);
		default:
			return ResponseEntity.ok().body(message);
		}
	}

	public void clearResponse() {
		status = HttpResponse.OK;
		message = "";
	}

	public ResponseEntity<String> getAndClearResponse() {
		ResponseEntity<String> response = getResponse();
		clearResponse();
		return response;
	}

}
