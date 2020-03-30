package crm.repositories;

import crm.entities.Provincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Provincia}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface ProvinciaRepository extends CrudRepository <Provincia, Short> {

    /**
     * Retorna una lista con las provincias segun un id dado
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Provincia}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    List<Provincia> findById(Short id);

    /**
     * Retorna una lista con todas las provincias de Chile segun el id del pais
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Provincia}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("select u from Provincia u where u.pais.id = 56 and (lower(u.nombre) != 'sin información') and u.region.id= :code order by u.nombre ASC")
    List<Provincia> findByProvinciaChileById(@Param("code") Short code);

    /**
     * Retorna un listado con todas las provincias de una region en particular segun su codigo.
     *
     * @return {@link java.util.List} de {@link crm.entities.Provincia}.
     */
    @Query("select u from Provincia u where u.pais.id = 56 and u.region.id = :code order by u.nombre asc")
    List<Provincia> findByRegionId(@Param("code") Short code);

    /**
     * Retorna una lista de provincias {@link crm.entities.Provincia} que pertenescan a un pais {@link crm.entities.Pais}
     *
     * @param id La id del pais
     *
     * @return {@link java.util.List} de {@link crm.entities.Provincia}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT u FROM Provincia u WHERE u.pais.id = :id ORDER BY u.nombre asc")
    List<Provincia> findByIdPais(@Param("id") Short id);

}
