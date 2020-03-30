package crm.repositories;

import crm.entities.Carrera;
import crm.entities.TipoGrado;
import crm.entities.TipoMoneda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoGrado}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoGradoRepository extends CrudRepository<TipoGrado, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoGrado} con todas los tipos de grados de la tabla dbo.tipo_grado
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoGrado}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoGrado> findAll();



    /**
     * Retorna un objeto de {@link crm.entities.TipoGrado} según un id dado
     *
     * @param codGrado Id del TipoGrado a buscar.
     * @return Instancia de {@link crm.entities.TipoGrado}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoGrado findByCodGrado(Short codGrado);
}
