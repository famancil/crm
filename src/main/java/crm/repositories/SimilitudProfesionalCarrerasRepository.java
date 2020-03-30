package crm.repositories;

import crm.entities.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link SimilitudProfesionalCarreras}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface SimilitudProfesionalCarrerasRepository extends CrudRepository<SimilitudProfesionalCarreras, SimilitudProfesionalCarrerasPK> {

    /**
     * Obtiene una lista con todas las {@link SimilitudProfesionalCarreras} registradas en el sistema
     *
     * @return Coleccion ({@link List}) de {@link SimilitudProfesionalCarreras}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<SimilitudProfesionalCarreras> findAll();




    /**
     * Obtiene una lista con todas las {@link SimilitudProfesionalCarreras}, según una {@link Carrera} especifica.
     *
     * @param codCarrera identificador de la carrera sobre la cual se desea obtener las {@link SimilitudProfesionalCarreras} asociadas a ella
     *
     * @return Coleccion ({@link List}) de {@link SimilitudProfesionalCarreras} de una carrera.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT s " +
            "FROM SimilitudProfesionalCarreras s " +
            "WHERE s.codCarrera = :codCarrera")
    List<SimilitudProfesionalCarreras> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Obtiene una lista con todas las {@link SimilitudProfesionalCarreras}, según una {@link Institucion} especifica.
     *
     * @param codInstitucion identificador de la {@link Institucion} sobre la cual se desea obtener las {@link SimilitudProfesionalCarreras} asociadas a ella
     *
     * @return Coleccion ({@link List}) de {@link SimilitudProfesionalCarreras} de una {@link Institucion}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT s " +
            "FROM SimilitudProfesionalCarreras s " +
            "WHERE s.codInstitucion = :codInstitucion")
    List<SimilitudProfesionalCarreras> buscarPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);




    /**
     * Actualiza la {@link Carrera} asociada de un {@link SimilitudProfesionalCarreras} especifico
     *
     * @param codInstitucionBuscar Id de {@link Institucion} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link Carrera} al que se le desea setear el valor
     * @param similitudBuscar Id de {@link SimilitudProfesionalCarreras} al que se le desea setear el valor
     * @param codCarreraSetear Nuevo Id de {@link Carrera} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE SimilitudProfesionalCarreras AS s " +
                    "SET s.codCarrera = :codCarreraSetear " +
                    "WHERE s.codCarrera = :codCarreraBuscar " +
                        "AND s.codInstitucion = :codInstitucionBuscar " +
                        "AND s.similitud = :similitudBuscar")
    void actualizarCodCarrera(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("similitudBuscar") String similitudBuscar, @Param("codCarreraSetear") Long codCarreraSetear);




    /**
     * Actualiza la {@link Institucion} asociada de un {@link SimilitudProfesionalCarreras} especifico
     *
     * @param codInstitucionBuscar Id de {@link Institucion} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link Carrera} al que se le desea setear el valor
     * @param similitudBuscar Id de {@link SimilitudProfesionalCarreras} al que se le desea setear el valor
     * @param codInstitucionSetear Nuevo Id de {@link Institucion} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE SimilitudProfesionalCarreras AS s " +
                    "SET s.codInstitucion = :codInstitucionSetear " +
                    "WHERE s.codCarrera = :codCarreraBuscar " +
                    "AND s.codInstitucion = :codInstitucionBuscar " +
                    "AND s.similitud = :similitudBuscar")
    void actualizarCodInstitucion(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("similitudBuscar") String similitudBuscar, @Param("codInstitucionSetear") Short codInstitucionSetear);




    /**
     * Elimina una {@link CarreraGrupo} segun el id especificados como parametro
     *
     * @param codInstitucionBuscar Id de {@link Institucion} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link Carrera} al que se le desea setear el valor
     * @param similitudBuscar Id de {@link SimilitudProfesionalCarreras} al que se le desea setear el valor
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM SimilitudProfesionalCarreras AS s " +
                    "WHERE s.codCarrera = :codCarreraBuscar " +
                        "AND s.codInstitucion = :codInstitucionBuscar " +
                        "AND s.similitud = :similitudBuscar")
    void eliminar(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("codInstitucionBuscar") Short codInstitucionBuscar, @Param("similitudBuscar") String similitudBuscar);

}
