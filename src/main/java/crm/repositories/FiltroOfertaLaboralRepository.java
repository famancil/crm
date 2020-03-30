package crm.repositories;

import crm.entities.AptitudUsuario;
import crm.entities.FiltroOfertaLaboral;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.FiltroOfertaLaboral}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface FiltroOfertaLaboralRepository extends CrudRepository<FiltroOfertaLaboral, Integer> {


    FiltroOfertaLaboral findById(Integer id);



    /**
     * Retorna una List con {@link crm.entities.FiltroOfertaLaboral} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.FiltroOfertaLaboral} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT f FROM FiltroOfertaLaboral AS f " +
                "WHERE f.usuario.id = :idUsuario ")
    List<FiltroOfertaLaboral> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.FiltroOfertaLaboral}
     *
     * @param idFiltroOfertaLaboralBuscar Id del {@link crm.entities.FiltroOfertaLaboral} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE FiltroOfertaLaboral AS f " +
                    "SET f.usuario.id = :idUsuarioSetear " +
                    "WHERE f.id = :idFiltroOfertaLaboralBuscar")
    void actualizarUsuarioId(@Param("idFiltroOfertaLaboralBuscar") Integer idFiltroOfertaLaboralBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.FiltroOfertaLaboral} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.FiltroOfertaLaboral}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM FiltroOfertaLaboral AS f " +
                    "WHERE f.id = :idBuscar")
    void eliminar(@Param("idBuscar") Integer idBuscar);

    /**
     * Elimina un {@link crm.entities.FiltroOfertaLaboral} segun la id  del usuario especificado como parametro
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM FiltroOfertaLaboral AS f " +
            "WHERE f.usuario.id = :idUsuarioBuscar")
    void eliminarPorIdUsuario(@Param("idUsuarioBuscar") Long idUsuarioBuscar);




    /**
     * Obtiene un listado de  {@link crm.entities.FiltroOfertaLaboral}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} a buscar
     *
     * @return  Lista con {@link crm.entities.AntecedenteEducacional}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT f FROM FiltroOfertaLaboral AS f " +
            "WHERE f.carrera.codCarrera = :codCarrera ")
    List<FiltroOfertaLaboral> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




}
