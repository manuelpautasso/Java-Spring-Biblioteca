package mp.service;

import java.util.List;

import mp.domain.Libro;
import mp.domain.Prestaciones;
import mp.domain.Usuario;
import mp.dto.PrestacionesResponse;

public interface PrestacionesService {
	/**
     * Retorna la lista de todas las prestaciones     
     * @return La lista de todas las prestaciones  
     */
	List<PrestacionesResponse> listarTodasPrestaciones();
	
	/**
     * Retorna la lista de todas las prestaciones activas (de los libros que aún no fueron devueltos)  
     * @return La lista de todas las prestaciones activas
     */
	List<PrestacionesResponse> listarTodasPrestacionesActivas();
	
	/**
     * Retorna la lista del historial de todas las prestaciones de un usuario
     * @param usuarioId, el id del usuario a buscar su historial
     * @return La lista del historial de las prestaciones del usuario
     */
	List<PrestacionesResponse> historialPrestacionesPorUsuarioId(int usuarioId);
	
	/**
     * Retorna la lista del historial de todas las prestaciones de un libro
     * @param libroId, el id del libro a buscar su historial
     * @return La lista del historial de las prestaciones del libro
     */
	List<PrestacionesResponse> historialPrestacionesPorLibroId(int libroId);
	
	/**
     * Retorna la lista de todas las prestaciones activas de un usuario
     * @param usuarioId, el id del usuario a buscar sus prestaciones activas
     * @return La lista de las prestaciones activas del usuario
     */
	List<PrestacionesResponse> prestacionesActivasPorUsuarioId(int usuarioId);
	
	/**
     * Retorna la lista de todas las prestaciones activas de un libro
     * @param libroId, el id del libro a buscar sus prestaciones activas
     * @return La lista de las prestaciones activas del libro
     */
	List<PrestacionesResponse> prestacionesActivasPorLibroId(int libroId);
	
	/**
     * Realiza el prestamo de un libro a un usuario, y actualiza el stock disponible del libro
     * @param libro, el libro a prestarse
     * @param usuario, el usuario que toma prestado el libro
     */
	void prestarLibro(Libro libro, Usuario usuario);
	
	/**
     * Realiza la devolucion de un prestamo y actualizamos el stock disponible del libro
     * @param id, id del prestamo a cancelar
     * @exception Si no se encuentra ningun prestamo con el id
     * @exception Si el prestamo ya estaba cancelado
     */
	void devolucionLibroById(int id);
	
	/**
     * Realiza la devolucion de un prestamo y actualizamos el stock disponible del libro
     * @param libroId, id del libro a ser devuelto
     * @param usuarioId, id del usuario que devuelve el libro
     * @exception Si no se encuentra ningun prestamo con el id del libro y el id del usuario
     * @exception Si el prestamo ya estaba cancelado
     */
	void devolucionLibroByLibroIdAndUsuarioId(int libroId, int usuarioId);
	
	/**
     * Retorna un boolean true si existe una prestacion activa de un libro por un usuario
     * @param libro, el libro a buscar si esta prestado
     * @param usuario, el usuario a buscar si tiene el libro
     * @return boolean si existe una prestacion activa del libro al usuario
     */
	boolean hayPrestacionActiva(Libro libro, Usuario usuario);
}
