package crm.repositories;

import crm.entities.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Region}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface RegionRepository extends CrudRepository <Region, Short>{
    /**
     * Retorna una lista con todas las regiones
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Region}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    List<Region> findAll();



    /**
     * Obtiene una {@link crm.entities.Region} según id de busqqueda
     *
     * @param id Id de la {@link crm.entities.Region} a obtener.
     *
     * @return Retorna {@link crm.entities.Region} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    Region findById(Short id);
}
