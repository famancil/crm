package crm.repositories;

import crm.entities.EmailMensaje;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EmailMensaje}
 *
 * @author ignacio oneto <ignacio.oneto@alumnos.usm.cl>
 */
public interface EmailMensajeRepository extends CrudRepository<EmailMensaje, Integer> {

}
