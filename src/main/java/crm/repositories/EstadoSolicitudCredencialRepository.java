package crm.repositories;

import crm.entities.EstadoSolicitudCredencial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EstadoSolicitudCredencial}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface EstadoSolicitudCredencialRepository extends CrudRepository <EstadoSolicitudCredencial, Short>{


    /**
     * Retorna el listado de todos los {@link crm.entities.EstadoSolicitudCredencial} que se encuentren en la base de datos.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoSector}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<EstadoSolicitudCredencial> findAll();



    /**
     * Retorna una instancia de {@link crm.entities.EstadoSolicitudCredencial}, según el codigo
     *
     * @param codigo
     *
     * @return Instancia de {@link crm.entities.EstadoSolicitudCredencial}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    EstadoSolicitudCredencial findByCodigo(Short codigo);


    /**
     * Retorna una lista con todas los {@link crm.entities.EstadoSolicitudCredencial}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.EstadoSolicitudCredencial}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<EstadoSolicitudCredencial> findAllByOrderByNombreAsc();
}
