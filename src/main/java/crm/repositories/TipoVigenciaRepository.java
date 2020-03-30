package crm.repositories;

import crm.entities.TipoGrado;
import crm.entities.TipoVigencia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoVigencia}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoVigenciaRepository extends CrudRepository<TipoVigencia, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoVigencia} con todas los tipos de grados de la tabla dbo.tipo_vigencia
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoVigencia}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoVigencia> findAll();



    /**
     * Retorna un objeto de {@link crm.entities.TipoVigencia} según un id dado
     *
     * @param codVigencia Id del TipoVigencia a buscar.
     * @return Instancia de {@link crm.entities.TipoVigencia}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoVigencia findByCodVigencia(Short codVigencia);
}
