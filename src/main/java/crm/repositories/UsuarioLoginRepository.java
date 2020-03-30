package crm.repositories;

import crm.entities.LoginAexa;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.LoginAexa}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface UsuarioLoginRepository extends CrudRepository<LoginAexa, Long> {

    /**
     * Retorna una instacia de {@link crm.entities.LoginAexa} cuyo username sea igual al parametro
     * 'username'.
     *
     * @param username nombre de usuario utilizado en la busqueda.
     * @return instancia de la clase {@link crm.entities.LoginAexa}.
     */
    public LoginAexa findByUsername(String username);

}
