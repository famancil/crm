package crm.repositories;

import crm.entities.Institucion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface InstitucionRepository extends CrudRepository<Institucion, Short> {

    /**
     * Retorna una lista con todas las instituciones
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Institucion> findAll();


    /**
     * Retorna una instancia de {@link crm.entities.Institucion}
     *
     * @param codInstitucion id de la institucion a buscar
     *
     * @return Objeto de {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    Institucion findByCodInstitucion(Short codInstitucion);

    /**
     * Retorna una lista con todas las instituciones que posean un nombre en especifico (o calce de nombre),
     * y id de un pais y un tipo de vigencia entregado calce en alguna parte de el.
     *
     * @param nombre nombre de la institucion que se desea buscar.
     * @param idPais Pais que se desea buscar.
     * @param codVigencia Tipo de vigencia que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion} con un cierto nombre.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD (soluciona el hecho que exista un valor nulo al momento de buscar un año con el comodin '%' "
    @Query(value="SELECT i " +
                "FROM Institucion i " +
                "WHERE COALESCE(TO_CHAR(i.vigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia " +
                    "AND COALESCE(TO_CHAR(i.pais.id, 'FM9999'), '') LIKE :idPais " +
                    "AND UPPER(translate(i.nomInstitucion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "ORDER BY i.nomInstitucion ASC",
            countQuery = "SELECT COUNT(i) " +
                    "FROM Institucion i " +
                    "WHERE COALESCE(TO_CHAR(i.vigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia " +
                        "AND COALESCE(TO_CHAR(i.pais.id, 'FM9999'), '') LIKE :idPais " +
                        "AND UPPER(translate(i.nomInstitucion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " )
    Page<Institucion> busquedaInstitucionPorCalceNombreYPaisYVigencia(@Param("nombre") String nombre, @Param("idPais") String idPais, @Param("codVigencia") String codVigencia, Pageable page);





    /**
     * Retorna una lista con todas las instituciones que posean un nombre en especifico o
     * que el parametro entregado calce en alguna parte de el.
     *
     * @param nombreInstitucion nombre de la institucion que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion} con un cierto nombre.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value="SELECT i FROM Institucion i WHERE UPPER(translate(i.nomInstitucion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreInstitucion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') ORDER BY i.nomInstitucion ASC" )
    List<Institucion> buscarInstitucionPorCalceNombre(@Param("nombreInstitucion") String nombreInstitucion);



    /**
     * Retorna una {@link crm.entities.Institucion} que posea un nombre específico (nombre exacto) al buscado
     * (Se hace una comparación haciendo un UPPER tanto al parámetro buscado como a lo que está en la BD)
     *
     * @param nombre nombre de Institucion que se desean buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT i " +
            "FROM Institucion AS i " +
            "WHERE UPPER(translate(i.nomInstitucion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ")
    Institucion buscarInstitucionPorNombreEspecifico(@Param("nombre") String nombre);




    /**
     * Retorna una lista con todas las instituciones que pertenezcan a un pais en especifico.
     *
     * @param idPais nombre de la institucion que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion} con un cierto nombre.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "SELECT i FROM Institucion i WHERE i.pais.id = :idPais ORDER BY i.nomInstitucion ASC")
    List<Institucion> findInstitucionPorPais(@Param("idPais")Short idPais);
}

