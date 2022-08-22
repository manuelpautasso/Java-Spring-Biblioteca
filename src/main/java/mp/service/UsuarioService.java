package mp.service;

import java.util.List;
import java.util.Optional;

import mp.domain.Usuario;

public interface UsuarioService {
	/**
     * Retorna una lista de todos los usuarios     
     * @return La lista de usuarios
     */
	List<Usuario> listarUsuarios();
	
	/**
     * Crea el usuario con el Rol de USER
     * @param genero, el genero a crear
     * @exception si ya existe un usuario con el mismo username o email
     * @return El usuario guardado con su id generado
     */
	Usuario crearUsuario(Usuario usuario);
	
	/**
     * Retorna un usuario por username
     * @param username, el username del usuario a retornar
     * @return El usuario encontrado por username o null de no encontrarse
     */
	Optional<Usuario> buscarUsuarioPorUsername(String username);
	
	/**
     * Retorna el id de un usuario buscando por username
     * @param username, el username del usuario a buscar
     * @return El id del usuario encontrado por username o 0 de no encontrarse
     */
	int buscarIdUsuarioPorUsername(String username);
	
	/**
     * Agrega un rol a un usuario.     
     * @param username y rolNombre, el username del usuario y el nombre del rol que se le requiere asignar     
     * @exception El usuario no es encotrado por su username
     * @exception El usuario ya tiene el rol que se le esta pidiendo agregar
     */
	void agregarRolAUsuario(String username, String rolNombre);
}
