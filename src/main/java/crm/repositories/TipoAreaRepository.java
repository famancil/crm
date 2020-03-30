package crm.repositories;

import crm.entities.TipoArea;
import crm.entities.TipoCargo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link TipoArea}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoAreaRepository extends CrudRepository<TipoArea, Short>{

    /**
     * Retorna una lista de {@link TipoArea}
     *
     * @return Coleccion ({@link List}) de {@link TipoArea}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoArea> findAll();



    /**
     * Retorna una lista de {@link TipoArea}, ordenadas por el nombre
     *
     * @return Coleccion ({@link List}) de {@link TipoArea}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoArea> findAllByOrderByNombreAsc();



    /**
     * Obtiene una {@link crm.entities.TipoArea} según id de busqqueda
     *
     * @param id Id de la {@link crm.entities.TipoArea} a obtener.
     *
     * @return Retorna {@link crm.entities.TipoArea} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoArea findByCodigo(Short id);



}
