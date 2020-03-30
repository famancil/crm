package crm.repositories;

import crm.entities.CapacitacionExalumno;
import crm.entities.ConocimientoInfoExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CapacitacionExalumno}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CapacitacionExalumnoRepository extends CrudRepository<CapacitacionExalumno, Long> {




    /**
     * Retorna una List con {@link crm.entities.CapacitacionExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CapacitacionExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CapacitacionExalumno AS c WHERE c.usuario.id = :idUsuario ")
    List<CapacitacionExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CapacitacionExalumno}
     *
     * @param idCapacitacionExalumnoBuscar Id del {@link crm.entities.CapacitacionExalumno} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CapacitacionExalumno AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idCapacitacionExalumnoBuscar")
    void actualizarUsuarioId(@Param("idCapacitacionExalumnoBuscar") Long idCapacitacionExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.CapacitacionExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.CapacitacionExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CapacitacionExalumno AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);





}
