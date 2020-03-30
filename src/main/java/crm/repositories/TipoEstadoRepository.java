package crm.repositories;

import crm.entities.TipoEstado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstado}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoEstadoRepository extends CrudRepository<TipoEstado, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoEstado} con todas los tipos de estado de la tabla org.tipo_estado,
     * en los que puede estar un contacto (reunión) con una {@link crm.entities.Empresa}.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoEstado}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoEstado> findAll();



    /**
     * Retorna un objeto {@link crm.entities.TipoEstado} según un identificador dado
     *
     * @param codEstado Identificador del TipoEstado a buscar.
     *
     * @return Instancia de {@link crm.entities.TipoEstado}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoEstado findByCodEstado(Short codEstado);
}
