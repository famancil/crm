package crm.repositories;

import crm.entities.CapacitacionExalumno;
import crm.entities.InvitacionVideoEntrevistaUsmEmpleo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface InvitacionVideoEntrevistaUsmEmpleoRepository extends CrudRepository<InvitacionVideoEntrevistaUsmEmpleo, Long> {




    /**
     * Retorna una List con {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT i FROM InvitacionVideoEntrevistaUsmEmpleo AS i WHERE i.usuario.id = :idUsuario ")
    List<InvitacionVideoEntrevistaUsmEmpleo> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo}
     *
     * @param idInvitacionVideoEntrevistaUsmEmpleoBuscar Id del {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo}
     *                                                   al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE InvitacionVideoEntrevistaUsmEmpleo AS i SET i.usuario.id = :idUsuarioSetear WHERE i.id = :idInvitacionVideoEntrevistaUsmEmpleoBuscar")
    void actualizarUsuarioId(@Param("idInvitacionVideoEntrevistaUsmEmpleoBuscar") Long idInvitacionVideoEntrevistaUsmEmpleoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.InvitacionVideoEntrevistaUsmEmpleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM InvitacionVideoEntrevistaUsmEmpleo AS i WHERE i.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);





}
