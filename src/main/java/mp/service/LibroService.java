package mp.service;

import java.util.List;

import mp.domain.Libro;

public interface LibroService {
	List<Libro> buscarTodos();
	Libro buscarPorNombre(String nombre);
	List<Libro> buscarPorParteDeNombre(String nombre);
	Libro buscarPorId(int id);
	void crear(Libro libro);
	void actualizar(Libro libro);
	void eliminar(int id);
	Boolean existeLibroPorId(int libroId);
	Boolean hayStockDisponible(int libroId);
}
