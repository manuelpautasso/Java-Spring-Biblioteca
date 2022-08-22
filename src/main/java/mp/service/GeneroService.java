package mp.service;

import java.util.List;

import mp.domain.Genero;

public interface GeneroService {
	/**
     * Retorna una lista de todos los generos     
     * @return La lista de todos los generos
     */
	List<Genero> buscarTodos();
	
	/**
     * Retorna un genero por ID
     * @param id, el id del genero a buscar
     * @exception si no se encuentra el genero
     * @return El genero encontrado por ID, u ocurre una excepcion de no encontrarse 
     */
	Genero buscarPorId(int id);
	
	/**
     * Retorna un genero por nombre
     * @param nombre, el nombre del genero a retornar
     * @exception si no se encuentra el genero
     * @return El genero encontrado por nombre
     */
	Genero buscarPorNombre(String nombre);
	
	/**
     * Crea el genero
     * @param genero, el genero a crear
     * @exception si ya existe un genero con el mismo nombre
     */
	void crear(Genero genero);
	
	/**
     * Actualiza los datos del genero
     * @param genero, el genero actualizado
     * @exception si el genero a actualizar no es encontrado por su id
     */
	void actualizar(Genero genero);
	
	/**
     * Elimina un genero por ID
     * @param id, el id del genero a eliminar
     * @exception si el genero a eliminar no es encontrado por su id
     */
	void eliminar(int id);
	
	/**
     * Retorna un boolean true si el genero existe
     * @param nombre, el nombre del genero a buscar
     * @return boolean si existe el genero
     */
	boolean existGenero(String nombre);
}
