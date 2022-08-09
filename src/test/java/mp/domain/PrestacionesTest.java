package mp.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class PrestacionesTest {
	
	@Test
	void test() {
		Usuario usuario1 = new Usuario();
		usuario1.setUsername("Pedro");		
		
		Libro libro1 = new Libro();
		libro1.setNombre("El resplandor");		
		
		Prestaciones prest1 = new Prestaciones(libro1, usuario1);
		System.out.println(prest1.toString());		
		
	}

}
