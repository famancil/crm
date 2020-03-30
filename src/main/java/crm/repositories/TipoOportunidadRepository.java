package crm.repositories;

import crm.entities.TipoOportunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoOportunidad}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoOportunidadRepository extends CrudRepository<TipoOportunidad, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoOportunidad} con todas los tipos de oportunidad de la tabla org.tipo_oportunidad
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoOportunidad}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoOportunidad> findAll();



    /**
     * Retorna un objeto de {@link crm.entities.TipoOportunidad} según un id dado
     *
     * @param codOportunidad Id del TipoOportunidad a buscar.
     *
     * @return Instancia de {@link crm.entities.TipoOportunidad}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoOportunidad findByCodOportunidad(Short codOportunidad);

    /**
     * Retorna una lista de todos los tipos de interes  de {@link crm.entities.TipoOportunidad}.
     *
     *
     * @return Coleccion ({@link java.util.List}) de tipo de interes de {@link crm.entities.TipoOportunidad}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT top.tipoInteres FROM TipoOportunidad AS top")
    List<String> findAllTipoInteres();
}
