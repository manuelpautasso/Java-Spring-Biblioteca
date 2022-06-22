package mp.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
	private String username;
	private String password;
	private String email;
}
