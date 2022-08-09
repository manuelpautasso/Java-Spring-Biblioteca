package mp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PrestacionesResponse {
	private int id;		
	private LibroResponse libro;		
	private UsuarioResponse usuario;	
	private Date egreso;	
	private Date vencimiento;	
	private Date devuelto;
	
}
