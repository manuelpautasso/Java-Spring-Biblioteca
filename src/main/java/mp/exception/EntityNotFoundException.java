package mp.exception;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends PersistenceException {
	
		private String message;
		
		public EntityNotFoundException(String message) {
			this.message = message;
		}
		
		@Override
		public String getMessage() {
			return message;
		}
	

}
