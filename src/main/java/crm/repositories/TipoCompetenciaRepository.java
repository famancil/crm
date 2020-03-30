package crm.repositories;

import crm.entities.Idioma;
import crm.entities.TipoCompetencia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCompetencia}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoCompetenciaRepository extends CrudRepository<TipoCompetencia, Short>{


    /**
     * Obtiene una lista con todas los {@link crm.entities.TipoCompetencia} registradas en el sistenma
     *
     * @return Coleccion ({@link List}) de {@link crm.entities.TipoCompetencia}
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoCompetencia> findAll();

}
