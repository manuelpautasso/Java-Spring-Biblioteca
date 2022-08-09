package mp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestacionesRequest {
	private int id;
	private int libroId;
	private String username;
		
	public PrestacionesRequest(int libroId, String username) {
		this.libroId = libroId;
		this.username = username;
	};
	
	public PrestacionesRequest(int id) {
		this.id = id;
	}
}
