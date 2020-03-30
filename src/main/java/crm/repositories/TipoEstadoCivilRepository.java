package crm.repositories;

import crm.entities.TipoCargo;
import crm.entities.TipoEstadoCivil;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoCivil}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoEstadoCivilRepository extends CrudRepository<TipoEstadoCivil, Short>{

    /**
     * Retorna una lista con todas los {@link crm.entities.TipoEstadoCivil}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoEstadoCivil}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoEstadoCivil> findAllByOrderByNombreAsc();
}
