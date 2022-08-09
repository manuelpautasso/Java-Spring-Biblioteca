package mp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.Data;
import lombok.NoArgsConstructor;
import mp.exception.InvalidArgumentException;
import static mp.domain.EnumData.EXPIRATION_PRESTACIONES;

@Data
@Entity
@NoArgsConstructor
public class Prestaciones implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private Date egreso;
	
	private Date vencimiento;
	
	private Date devuelto;
	
	public Prestaciones(Libro libro, Usuario usuario) {
		setUsuario(usuario);
		setLibro(libro);
		setEgreso(new Date());
	}
	
	public Prestaciones(Libro libro, Usuario usuario, Date egreso) {
		setUsuario(usuario);
		setLibro(libro);
		setEgreso(egreso);
	}
	
	void setEgreso(Date date) {
		if(date.after(new Date())) {
			throw new InvalidArgumentException("La fecha de egereso no puede ser posterior a la actual.");
		}		
		this.egreso = date;
		this.setVencimiento(new Date(date.getTime() + EXPIRATION_PRESTACIONES.data));
	}
	
	public void setDevuelto() {
		this.devuelto = new Date();
	}
	
}
