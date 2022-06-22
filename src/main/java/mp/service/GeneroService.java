package mp.service;

import java.util.List;

import mp.domain.Genero;

public interface GeneroService {
	List<Genero> buscarTodos();
	Genero buscarPorId(int id);
	Genero buscarPorNombre(String nombre);
	void crear(Genero genero);
	void actualizar(Genero genero);
	void eliminar(int id);
}
