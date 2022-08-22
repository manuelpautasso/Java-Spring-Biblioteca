package mp.service;

import java.util.List;

import mp.domain.Libro;

public interface LibroService {
	/**
     * Retorna una lista de todos los libros     
     * @return La lista de libros
     */
	List<Libro> buscarTodos();
	
	/**
     * Retorna un libro por ID del libro
     * @param id, el id del libro a retornar
     * @exception si no se encuentra el libro
     * @return El libro encontrado por id
     */
	Libro buscarPorId(int id);
	
	/**
     * Retorna un libro por nombre del libro
     * @param nombre, el nombre del libro a retornar
     * @exception si no se encuentra el libro
     * @return El libro encontrado por nombre
     */
	Libro buscarPorNombre(String nombre);
	
	/**
     * Retorna una lista de libros que al menos una parte de su nombre contenga el 
     * @param nombre, el nombre del libro a retornar
     * @exception si no se encuentra ningun libro
     * @return El libro encontrado por nombre
     */
	List<Libro> buscarPorParteDeNombre(String nombre);
	
	/**
     * Crea el libro
     * @param libro, el libro a crear     
     */
	void crear(Libro libro);
	
	/**
     * Actualiza los datos del libro
     * @param libro, el libro actualizado
     * @exception si el libro a actualizar no es encontrado por su id
     */
	void actualizar(Libro libro);
	
	/**
     * Elimina un libro por ID
     * @param id, el id del libro a eliminar
     * @exception si el libro a eliminar no es encontrado por su id
     */
	void eliminar(int id);
	
	/**
     * Retorna un boolean true si el libro existe
     * @param id, el id del libro a buscar
     * @return boolean si existe el libro
     */
	Boolean existeLibroPorId(int libroId);
	
	/**
     * Retorna un boolean true si hay stock disponible para prestar del libro
     * @param id, el id del libro a buscar su stock
     * @return boolean si hay stock del libro
     */
	Boolean hayStockDisponible(int libroId);
	
	/**
     * Reduce el stock disponible en 1 de un libro. 
     * Esta función se usa cuando se presta un libro
     * @param id, el id del libro a reducir en 1 su stock 
     * @exception si el stock del libro es menor o igual a 0, este no puede reducirse   
     */
	void reducirEn1StockDisponible(int libroId);
	
	/**
     * Aumenta el stock disponible en 1 de un libro.
     * Esta función se usa cuando se devuelve un libro
     * @param id, el id del libro a aumentar en 1 su stock      
     */
	void aumentarEn1StockDisponible(int libroId);
}
