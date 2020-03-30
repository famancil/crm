package crm.repositories;

import crm.entities.TipoCargo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCargo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoCargoRepository extends CrudRepository<TipoCargo, Short>{

    /**
     * Retorna una lista de {@link crm.entities.TipoCargo}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoCargo}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoCargo> findAll();



    /**
     * Retorna una lista de {@link crm.entities.TipoCargo}, ordenadas por el nombre
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoCargo}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoCargo> findAllByOrderByNomCargoAsc();



    /**
     * Obtiene una {@link crm.entities.TipoCargo} según id de busqqueda
     *
     * @param id Id de la {@link crm.entities.TipoCargo} a obtener.
     *
     * @return Retorna {@link crm.entities.TipoCargo} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoCargo findByCodCargo(Short id);



}
