package crm.repositories;

import crm.entities.CampanaExalumno;
import crm.entities.ConocimientoInfoExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ConocimientoInfoExalumno}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface ConocimientoInfoExalumnoRepository extends CrudRepository<ConocimientoInfoExalumno, Long> {




    /**
     * Retorna una List con {@link crm.entities.ConocimientoInfoExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.ConocimientoInfoExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM ConocimientoInfoExalumno AS c WHERE c.usuario.id = :idUsuario ")
    List<ConocimientoInfoExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.ConocimientoInfoExalumno}
     *
     * @param idConocimientoInfoExalumnoBuscar Id del {@link crm.entities.ConocimientoInfoExalumno} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE ConocimientoInfoExalumno AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idConocimientoInfoExalumnoBuscar")
    void actualizarUsuarioId(@Param("idConocimientoInfoExalumnoBuscar") Long idConocimientoInfoExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.ConocimientoInfoExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.ConocimientoInfoExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ConocimientoInfoExalumno AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);





}
