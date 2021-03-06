package cn.blmdz.hbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound404Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFound404Exception() {
	}

	public NotFound404Exception(Throwable cause) {
		super(cause);
	}

	public NotFound404Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFound404Exception(String message) {
		super(message);
	}
}