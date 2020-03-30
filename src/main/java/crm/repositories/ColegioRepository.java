package crm.repositories;

import crm.entities.Colegio;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by exalumnos on 06-02-15.
 */
public interface ColegioRepository extends CrudRepository <Colegio, Integer>{

    Colegio findByCodigo(Integer cod);

    @Query(value = "SELECT c FROM Colegio c WHERE c.pais.id = :idPais ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.pais.id = :idPais")
    Page<Colegio> findByIdPais(@Param("idPais") Short idPais, Pageable page);

    @Query(value = "SELECT c FROM Colegio c WHERE c.comuna.codigo = :idComuna ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.pais.id = :idComuna")
    Page<Colegio> findByCodComuna(@Param("idComuna") Short idComuna, Pageable page);

    @Query(value = "SELECT c FROM Colegio c WHERE c.rbd = :rbd ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.rbd = :rbd")
    Page<Colegio> findByCodigoRBD(@Param("rbd") Integer rbd, Pageable page);

    @Query(value="SELECT c FROM Colegio c WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%') ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%')" )
    Page<Colegio> findByNombreOficial(@Param("nombre") String nombre, Pageable page);

    @Query(value = "SELECT c FROM Colegio c WHERE c.rbd = :rbd AND c.comuna.codigo = :idComuna ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.rbd = :rbd AND c.comuna.codigo = :idComuna")
    Page<Colegio> findByComunaAndRbd(@Param("idComuna") Short idComuna, @Param("rbd") Integer rbd, Pageable page);

    @Query(value="SELECT c FROM Colegio c WHERE c.rbd = :rbd AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%') ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.rbd = :rbd AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%')" )
    Page<Colegio> findByNombreAndRbd(@Param("nombre") String nombre, @Param("rbd") Integer rbd, Pageable page);

    @Query(value="SELECT c FROM Colegio c WHERE c.comuna.codigo = :idComuna AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%') ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.comuna.codigo = :idComuna AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%')" )
    Page<Colegio> findByNombreAndComuna(@Param("nombre") String nombre, @Param("idComuna") Short comuna, Pageable page);

    @Query(value="SELECT c FROM Colegio c WHERE c.comuna.codigo = :idComuna AND c.rbd = :rbd AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%') ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) FROM Colegio c WHERE c.comuna.codigo = :idComuna AND c.rbd = :rbd AND UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%')" )
    Page<Colegio> findByComunaNombreRbd(@Param("nombre") String nombre, @Param("idComuna") Short idComuna,@Param("rbd") Integer rbd, Pageable page);



    /**
     * Obtiene un listado de manera paginada {@link crm.entities.Colegio}, segun los valores a buscar.
     *
     * @param nombreColegio Nombre del colegio que se desea buscar.
     * @param codigoRbd Codigo rbd del colegio que se desea buscar.
     * @param idPais Id del pais que se desea buscar.
     * @param idComuna Id de la comuna que se desea buscar
     * @param codVigencia Tipo de Vigencia de las institucion a buscar
     *
     * @return Lista de {@link crm.entities.Colegio}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value = "SELECT c " +
                    "FROM Colegio AS c " +
                    "WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreColegio ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                            "AND COALESCE(TO_CHAR(c.rbd, 'FM999999'), '') LIKE :codigoRbd " +
                            "AND COALESCE(TO_CHAR(c.pais.id, 'FM9999'), '') LIKE :idPais " +
                            "AND COALESCE(TO_CHAR(c.comuna.codigo, 'FM9999'), '') LIKE :idComuna " +
                            "AND COALESCE(TO_CHAR(c.vigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia " +
                    "ORDER BY c.nombreOficial ASC",
            countQuery = "SELECT COUNT(c) " +
                        "FROM Colegio AS c " +
                        "WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreColegio ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                                "AND COALESCE(TO_CHAR(c.rbd, 'FM999999'), '') LIKE :codigoRbd " +
                                "AND COALESCE(TO_CHAR(c.pais.id, 'FM9999'), '') LIKE :idPais " +
                                "AND COALESCE(TO_CHAR(c.comuna.codigo, 'FM9999'), '') LIKE :idComuna " +
                                "AND COALESCE(TO_CHAR(c.vigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia ")
        // COALESCE es para el manejo de valores nulos en la BD (soluciona el hecho que exista un valor nulo al momento de buscar un año con el comodin '%'
    Page<Colegio> busquedaColegioPorCalceNombreYRbdYPaisYComunaYVigencia(@Param("nombreColegio") String nombreColegio, @Param("codigoRbd") String codigoRbd, @Param("idPais") String idPais, @Param("idComuna") String idComuna, @Param("codVigencia") String codVigencia, Pageable page );



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
    @Query("SELECT c " +
            "FROM Colegio AS c " +
            "WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ")
    Colegio buscarColegioPorNombreEspecifico(@Param("nombre") String nombre);

    /**
     * Retorna un {@link crm.entities.Colegio} que posea un codigo RBD igual al buscado
     *
     * @param rbd codigo RBD Institucion que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Institucion}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Colegio AS c " +
            "WHERE c.rbd = :rbd")
    List<Colegio> buscarColegioPorCodigoRBD(@Param("rbd") Integer rbd);

    /**
     * Retorna una lista con todas los colegios que pertenezcan a un pais en especifico.
     *
     * @param idPais .
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Colegio} con un cierto nombre.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @Query(value = "SELECT c FROM Colegio c WHERE c.pais.id = :idPais ORDER BY c.nombreOficial ASC")
    List<Colegio> findColegioPorPais(@Param("idPais")Short idPais);

    /**
     * Retorna una lista con todas los colegios que posean un nombre en especifico o
     * que el parametro entregado calce en alguna parte de el.
     *
     * @param nombreColegio nombre de la institucion que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Colegio} con un cierto nombre.
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @Query(value="SELECT c FROM Colegio c WHERE UPPER(translate(c.nombreOficial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreColegio,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') ORDER BY c.nombreOficial ASC" )
    List<Colegio> buscarColegioPorCalceNombre(@Param("nombreColegio") String nombreColegio);

    /**
     * Retorna una lista con todas las instituciones
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Colegio}.
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    List<Colegio> findAll();
}
