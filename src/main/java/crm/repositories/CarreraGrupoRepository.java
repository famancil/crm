package crm.repositories;

import crm.entities.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CarreraGrupo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CarreraGrupoRepository extends CrudRepository<CarreraGrupo, CarreraGrupoPK> {

    /**
     * Obtiene una lista con todas las {@link CarreraGrupo} registradas en el sistema
     *
     * @return Coleccion ({@link List}) de {@link CarreraGrupo}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<CarreraGrupo> findAll();




    /**
     * Obtiene una lista con todas las {@link crm.entities.CarreraGrupo}, según una {@link Carrera} especifica.
     *
     * @param codCarrera identificador de la carrera sobre la cual se desea obtener las {@link CarreraGrupo} asociadas a ella
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CarreraGrupo} de una carrera.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT cg " +
            "FROM CarreraGrupo cg " +
            "WHERE cg.codCarrera = :codCarrera")
    List<CarreraGrupo> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Actualiza la {@link Carrera} asociada de un {@link CarreraGrupo} especifico
     *
     * @param idGrupoUsmempleoBuscar Id de {@link crm.entities.GrupoEmpleo} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link Carrera} al que se le desea setear el valor
     * @param codCarreraSetear Nuevo Id de {@link Carrera} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CarreraGrupo AS cg " +
                    "SET cg.codCarrera = :codCarreraSetear " +
                    "WHERE cg.codCarrera = :codCarreraBuscar  " +
                        "AND cg.idGrupoUsmempleo = :idGrupoUsmempleoBuscar")
    void actualizarCodCarrera(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("idGrupoUsmempleoBuscar") Short idGrupoUsmempleoBuscar, @Param("codCarreraSetear") Long codCarreraSetear);




    /**
     * Elimina una {@link CarreraGrupo} segun el id especificados como parametro
     *
     * @param idGrupoUsmempleoBuscar Id de {@link crm.entities.GrupoEmpleo} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link Carrera} al que se le desea setear el valor
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CarreraGrupo AS cg " +
                    "WHERE cg.codCarrera = :codCarreraBuscar " +
                        "AND cg.idGrupoUsmempleo = :idGrupoUsmempleoBuscar")
    void eliminar(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("idGrupoUsmempleoBuscar") Short idGrupoUsmempleoBuscar);

}
