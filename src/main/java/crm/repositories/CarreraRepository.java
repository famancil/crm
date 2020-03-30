package crm.repositories;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Carrera}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CarreraRepository extends CrudRepository<Carrera, Short> {

    /**
     * Retorna una lista de {@link crm.entities.Carrera} con todas las carreras de la tabla dbo.carreras
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Carrera> findAllByOrderByNombreCarreraAsc();

    /**
     * Retorna una {@link crm.entities.Carrera} de la tabla dbo.carreras especificada por su Id
     *
     * @param codCarrera id de la carrera buscada
     * @return {@link crm.entities.Carrera} buscada
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    Carrera findByCodCarrera(Long codCarrera);


    /**
     * Retorna un listado de {@link crm.entities.Carrera} igual al parametro nombre.
     *
     * @param nombre nombre de carrera que se desean buscar.
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Carrera AS c " +
            "WHERE UPPER(translate(c.nombreCarrera,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombre) ,'%') " +
            "ORDER BY c.nombreCarrera ASC")
    List<Carrera> buscarCarrerasPorCalceNombre(@Param("nombre") String nombre);


    /**
     * Retorna un listado de {@link crm.entities.Carrera} que posean un calce
     * en nombre, abreviacion o titulo con el parametro buscado
     *
     * @param nombre nombre, abreviacion o titulo de carrera que se desean buscar.
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT c " +
            "FROM Carrera AS c " +
            "WHERE UPPER(translate(c.nombreCarrera,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "OR UPPER(translate(c.abreviacion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') " +
                "OR UPPER(translate(c.titulo,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') " +
            "ORDER BY c.nombreCarrera ASC")
    List<Carrera> buscarCarrerasPorCalceNombreOAbreviacionOTitulo(@Param("nombre") String nombre);




    /**
     * Retorna un listado de {@link crm.entities.Carrera}, de manera paginada, que posean un calce
     * en nombre, abreviacion o titulo con el parametro buscado
     *
     * @param nombre nombre, abreviacion o titulo de carrera que se desean buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // COALESCE es para el manejo de valores nulos en la BD (soluciona el hecho que exista un valor nulo al momento de buscar un año con el comodin '%' "
    @Query(value="SELECT DISTINCT c " +
            "FROM Carrera AS c " +
            "WHERE ( UPPER(translate(c.nombreCarrera,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "OR UPPER(translate(c.abreviacion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') " +
                "OR UPPER(translate(c.titulo,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') )" +
                "AND COALESCE(TO_CHAR(c.tipoVigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia " +
            "ORDER BY c.nombreCarrera ASC",
            countQuery = "SELECT COUNT(DISTINCT c) " +
                            "FROM Carrera AS c " +
                            "WHERE ( UPPER(translate(c.nombreCarrera,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                                    "OR UPPER(translate(c.abreviacion,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') " +
                                    "OR UPPER(translate(c.titulo,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  ,'%') ) " +
                                    "AND COALESCE(TO_CHAR(c.tipoVigencia.codVigencia, 'FM9999'), '') LIKE :codVigencia " )
    Page<Carrera> buscarCarrerasPorCalceNombreOAbreviacionOTituloYVigencia(@Param("nombre") String nombre, @Param("codVigencia") String codVigencia, Pageable page);



    /**
     * Retorna una {@link crm.entities.Carrera} que posea un nombre específico (nombre exacto) al buscado
     * (Se hace una comparación haciendo un UPPER tanto al parámetro buscado como a lo que está en la BD)
     *
     * @param nombre nombre de carrera que se desean buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Carrera AS c " +
            "WHERE UPPER(translate(c.nombreCarrera,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ")
    Carrera buscarCarrerasPorNombreEspecifico(@Param("nombre") String nombre);




    /**
     * Obtiene un listado de todas las {@link crm.entities.Carrera},
     * que no esten asociadas a una {@link crm.entities.Institucion} específica y que las {@link crm.entities.Carrera}
     * posean un tipo de vigencia "Vigente" o "No Vigente"
     *
     * @param codInstitucion Id de la Institucion que no se quieren mostrar sus carreras asociadas
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraInstitucion}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM Carrera AS c " +
            "WHERE c NOT IN " +
            "(SELECT DISTINCT ci.carrera " +
                "FROM CarreraInstitucion AS ci " +
                "WHERE ci.institucion.codInstitucion = :codInstitucion ) " +
                    "AND ( c.tipoVigencia.codVigencia = 1 OR c.tipoVigencia.codVigencia = 2) " +
                "ORDER BY c.nombreCarrera ASC")
    List<Carrera> buscarCarrerasNoAsociadasAInstitucionPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);

    /**
     * Obtiene un listado de todas las {@link crm.entities.Carrera},
     * que esten asociadas a una institución específica.
     *
     * @param institucionId Id de la Institucion a la cual se le quieren mostrar sus carreras asociadas
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Carrera}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT c from CarreraInstitucion AS ci JOIN ci.carrera AS c WHERE ci.codInstitucion = :institucionId ORDER BY c.nombreCarrera ASC")
    List<Carrera> buscarCarrerasPorCodInstitucion(@Param("institucionId") Short institucionId);

    @Query("SELECT COUNT(CASE WHEN c.tipoVigencia.codVigencia = 1 THEN 1 END), " +
            "COUNT(CASE WHEN c.tipoVigencia.codVigencia = 2 THEN 1 END), " +
            "COUNT(CASE WHEN c.tipoVigencia.codVigencia = 4 THEN 1 END) " +
            "FROM Carrera c")
    String contarCarrerasPorVigencia();
}
