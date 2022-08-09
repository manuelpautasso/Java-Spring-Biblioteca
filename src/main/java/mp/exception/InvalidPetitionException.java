package mp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPetitionException extends RuntimeException {
	private String message;

	public InvalidPetitionException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
