package crm.repositories;

import crm.entities.Idioma;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Idioma}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface IdiomaRepository extends CrudRepository<Idioma, Short>{


    /**
     * Obtiene una lista con todas los {@link crm.entities.Idioma} registradas en el sistenma
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Idioma}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Idioma> findAll();

}
