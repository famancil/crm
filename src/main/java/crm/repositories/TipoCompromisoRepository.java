package crm.repositories;

import crm.entities.TipoCompromiso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCompromiso}.
 *
 * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
 */
public interface TipoCompromisoRepository extends CrudRepository<TipoCompromiso,Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoCompromiso} con todas los tipos de compromiso de la tabla crm.tipo_compromiso
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoCompromiso}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    List<TipoCompromiso> findAll();



    /**
     * Retorna un objeto de {@link crm.entities.TipoCompromiso} según un id dado
     *
     * @param codCompromiso Id del TipoCompromiso a buscar.
     *
     * @return Instancia de {@link crm.entities.TipoCompromiso}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoCompromiso findByCodCompromiso(Short codCompromiso);

    /**
     * Retorna una lista de todos los tipos de compromisos  de {@link crm.entities.TipoCompromiso}.
     *
     *
     * @return Coleccion ({@link java.util.List}) de tipo de compromisos de {@link crm.entities.TipoCompromiso}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT top.nomCompromiso FROM TipoCompromiso AS top")
    List<String> findAllTipoCompromiso();
}
