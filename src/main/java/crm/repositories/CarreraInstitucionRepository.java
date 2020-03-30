package crm.repositories;

import crm.entities.Carrera;
import crm.entities.CarreraInstitucion;
import crm.entities.CarreraInstitucionPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CarreraInstitucion}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CarreraInstitucionRepository extends CrudRepository<CarreraInstitucion, CarreraInstitucionPK> {

    /**
     * Obtiene una lista con todas las {@link crm.entities.CarreraInstitucion} registradas en la tabla carrera_institucion
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraInstitucion}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<CarreraInstitucion> findAll();


    /**
     * Obtiene una lista con todas las {@link crm.entities.CarreraInstitucion} asociadas a una institución específica
     *
     * @param codInstitucion identificador de la institucion sobre la cual se desea obtener las carrerasInstitucion asociadas a ella
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraInstitucion} de una institucion.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT ci " +
            "FROM CarreraInstitucion ci " +
            "JOIN ci.institucion AS i " +
            "JOIN ci.carrera AS c " +
            "WHERE i.codInstitucion = :codInstitucion " +
            "ORDER BY c.nombreCarrera ASC")
    List<CarreraInstitucion> buscarPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);




    /**
     * Obtiene una lista con todas las {@link crm.entities.CarreraInstitucion} asociadas a una institución específica
     *
     * @param codCarrera identificador de la carrera sobre la cual se desea obtener las carrerasInstitucion asociadas a ella
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraInstitucion} de una carrera.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT ci " +
            "FROM CarreraInstitucion ci " +
            "JOIN ci.carrera AS c " +
            "WHERE c.codCarrera = :codCarrera " +
            "ORDER BY c.nombreCarrera ASC")
    List<CarreraInstitucion> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Obtiene una lista con todas las {@link crm.entities.CarreraInstitucion} asociadas a una institución específica
     *
     * @param codInstitucion identificador de la institucion sobre la cual se desea obtener las carrerasInstitucion asociadas a ella
     * @param page datos que permiten la paginación.
     *
     * @return Coleccion {@Link java.util.Page} de {@link crm.entities.CarreraInstitucion} de una institucion.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value = "SELECT c " +
                    "FROM CarreraInstitucion c " +
                    "WHERE c.institucion.codInstitucion = :codInstitucion " +
                    "ORDER BY c.carrera.nombreCarrera ASC",
            countQuery = "SELECT COUNT (c) " +
                    "FROM CarreraInstitucion c " +
                    "WHERE c.institucion.codInstitucion = :codInstitucion ")
    Page<CarreraInstitucion> buscarCarreraInstitucionPorCodInstitucion( @Param("codInstitucion") Short codInstitucion,
                                                                        Pageable page);




    /**
     * Obtiene una {@link crm.entities.CarreraInstitucion} especifica, según su identificador de Carrera e Institucion
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraInstitucion}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    CarreraInstitucion findByCodCarreraAndCodInstitucion(Long codCarrera, Short codInstitucion);




    /**
     * Actualiza la {@link crm.entities.Carrera} asociada de un {@link crm.entities.CarreraInstitucion}
     *
     * @param codInstitucionBuscar Id de {@link crm.entities.Institucion} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link crm.entities.Carrera} al que se le desea setear el valor
     * @param codCarreraSetear Nuevo Id de {@link crm.entities.Carrera} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CarreraInstitucion AS ci " +
                    "SET ci.codCarrera = :codCarreraSetear " +
                    "WHERE ci.codInstitucion = :codInstitucionBuscar " +
                        "AND ci.codCarrera = :codCarreraBuscar")
    void actualizarCodCarrera(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("codCarreraSetear") Long codCarreraSetear);





    /**
     * Actualiza la {@link crm.entities.Institucion} asociada de un {@link crm.entities.CarreraInstitucion}
     *
     * @param codInstitucionBuscar Id de {@link crm.entities.Institucion} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link crm.entities.Carrera} al que se le desea setear el valor
     * @param codInstitucionSetear Nuevo Id de {@link crm.entities.Institucion} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CarreraInstitucion AS ci " +
            "SET ci.codInstitucion = :codInstitucionSetear " +
            "WHERE ci.codInstitucion = :codInstitucionBuscar " +
            "AND ci.codCarrera = :codCarreraBuscar")
    void actualizarCodInstitucion(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("codInstitucionSetear") Short codInstitucionSetear);





    /**
     * Elimina una {@link crm.entities.CarreraInstitucion} segun el id especificados como parametro
     *
     * @param idCarreraBuscar Id de la {@link crm.entities.Carrera}
     * @param codInstitucionBuscar Id de la {@link crm.entities.Institucion}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CarreraInstitucion AS ci " +
                    "WHERE ci.codCarrera = :idCarreraBuscar " +
                        "AND ci.codInstitucion = :codInstitucionBuscar")
    void eliminar(@Param("idCarreraBuscar") Long idCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar);

}
