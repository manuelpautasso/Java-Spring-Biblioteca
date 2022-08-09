package mp.service;

import java.util.List;

import mp.domain.Libro;
import mp.domain.Prestaciones;
import mp.domain.Usuario;
import mp.dto.PrestacionesResponse;

public interface PrestacionesService {
	List<PrestacionesResponse> listarTodasPrestaciones();
	List<PrestacionesResponse> listarTodasPrestacionesActivas();
	List<PrestacionesResponse> historialPrestacionesPorUsuarioId(int usuarioId);
	List<PrestacionesResponse> historialPrestacionesPorLibroId(int libroId);
	List<PrestacionesResponse> prestacionesActivasPorUsuarioId(int usuarioId);
	List<PrestacionesResponse> prestacionesActivasPorLibroId(int libroId);
	void prestarLibro(Libro libro, Usuario usuario);
	void devolucionLibroById(int id);
	void devolucionLibroByLibroIdAndUsuarioId(int libroId, int usuarioId);
	boolean hayPrestacionActiva(Libro libro, Usuario usuario);
}
