package crm.repositories;

import crm.entities.ContactoHistoricoEmpresa;
import crm.entities.VideoEntrevistaUsmEmpleo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.VideoEntrevistaUsmEmpleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface VideoEntrevistaUsmEmpleoRepository extends CrudRepository<VideoEntrevistaUsmEmpleo, Long> {




    /**
     * Retorna una List con {@link crm.entities.VideoEntrevistaUsmEmpleo} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.VideoEntrevistaUsmEmpleo} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT v FROM VideoEntrevistaUsmEmpleo AS v WHERE v.usuario.id = :idUsuario ")
    List<VideoEntrevistaUsmEmpleo> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.VideoEntrevistaUsmEmpleo}
     *
     * @param idVideoEntrevistaUsmEmpleoBuscar Id del {@link crm.entities.VideoEntrevistaUsmEmpleo} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE VideoEntrevistaUsmEmpleo AS v SET v.usuario.id = :idUsuarioSetear WHERE v.id = :idVideoEntrevistaUsmEmpleoBuscar")
    void actualizarUsuarioId(@Param("idVideoEntrevistaUsmEmpleoBuscar") Long idVideoEntrevistaUsmEmpleoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.VideoEntrevistaUsmEmpleo} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.VideoEntrevistaUsmEmpleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM VideoEntrevistaUsmEmpleo AS v WHERE v.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
