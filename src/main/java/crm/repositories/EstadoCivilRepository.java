package crm.repositories;

import crm.entities.TipoEstadoCivil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoCivil}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface EstadoCivilRepository extends CrudRepository<TipoEstadoCivil, Short> {
    /**
     * Retorna el listado de todos los {@link crm.entities.TipoEstadoCivil} que se encuentren en la base de datos.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoEstadoCivil}.
     */
    List<TipoEstadoCivil> findAll();



    /**
     * Retorna una instancia de {@link crm.entities.TipoEstadoCivil}, según el codigo
     *
     * @param codigo
     *
     * @return Instancia de {@link crm.entities.TipoEstadoCivil}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoEstadoCivil findByCodigo(Short codigo);

}
