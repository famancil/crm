package crm.repositories;

import crm.entities.Comuna;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Comuna}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface ComunaRepository extends CrudRepository <Comuna, Short>{

    /**
     * Retorna una lista con las comunas segun un id de pais y un id de provincia
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Comuna}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("select u from Comuna u where u.pais.id = :codePa and u.provincia.id = :codePr and lower(u.nombre)!='sin información' and u.nombre!=' ' order by u.nombre ASC")
    List<Comuna> findComunaByIdPaisAndIdProv(@Param("codePa") short codePa,@Param("codePr") short codePr);

    /**
     * Retorna una lista con las comunas segun un id de pais dado
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Comuna}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("select u from Comuna u where u.pais.id = :codePa and lower(u.nombre)!='sin información' and u.nombre!=' ' order by u.nombre ASC")
    List<Comuna> findComunaByIdPais(@Param("codePa") short codePa);

    /**
     * Retorna una instancia de {@link crm.entities.Comuna}
     * @param codigo
     * @return Objeto de {@link crm.entities.Comuna}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    Comuna findByCodigo(Short codigo);

    /**
     * Obtiene una lista de todas las comunas {@link crm.entities.Comuna} registradas en el sistema, ordenadas por el
     * nombre
     *
     * @return Collection ({@link java.util.List}) de {@link crm.entities.Comuna}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Comuna> findAllByOrderByNombreAsc();




    /**
     * Retorna una lista con todas las comunas {@link crm.entities.Comuna} que pertenecen a una  {@link crm.entities.Provincia}
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Comuna AS c " +
            "WHERE c.provincia.id = :idProvincia " +
            "ORDER BY c.nombre ASC")
    List<Comuna> buscarPorIdProvincia(@Param("idProvincia") Short idProvincia);


    /**
     * Regorna una lista de todas las comunas {@link crm.entities.Comuna} que pertenescan a una region {@link crm.entities.Region}
     *
     * @param idRegion la id de la region
     *
     * @return {@link java.util.List} de {@link crm.entities.Comuna}
     *
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Comuna AS c " +
            "WHERE c.provincia.region.id = :idRegion " +
            "ORDER BY c.nombre ASC")
    List<Comuna> buscarPorIdRegion(@Param("idRegion") Short idRegion);


    /**
     * Retorna una lista con todas las comunas {@link crm.entities.Comuna} que pertenecen a una  {@link crm.entities.Provincia}
     *
     * @return {@link java.util.List} de {@link crm.entities.Provincia}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Comuna AS c " +
            "WHERE c.codigo = :codigo ")
    Comuna buscarPorId(@Param("codigo") Short codigo);

}
