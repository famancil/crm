package crm.repositories;

import crm.entities.TipoNivelFacturacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoNivelFacturacion}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface TipoNivelFacturacionRepository extends CrudRepository<TipoNivelFacturacion, Short> {
    /**
     * Retorna el listado de todos los {@link crm.entities.TipoNivelFacturacion} que se encuentren en la base de datos
     * ordenados por codigo en forma ascendente.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoNivelFacturacion}.
     */
    List<TipoNivelFacturacion> findAllByOrderByCodNivelFacturacionAsc();

}

