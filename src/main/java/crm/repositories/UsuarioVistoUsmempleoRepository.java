package crm.repositories;

import crm.entities.CompetenciaExalumno;
import crm.entities.CompetenciaExalumnoPK;
import crm.entities.UsuarioVistoUsmEmpleo;
import crm.entities.UsuarioVistoUsmEmpleoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioVistoUsmEmpleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface UsuarioVistoUsmempleoRepository extends CrudRepository<UsuarioVistoUsmEmpleo, UsuarioVistoUsmEmpleoPK> {



    /**
     * Retorna una List con {@link crm.entities.UsuarioVistoUsmEmpleo} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.UsuarioVistoUsmEmpleo} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT u FROM UsuarioVistoUsmEmpleo AS u WHERE u.idUsuario = :idUsuario ")
    List<UsuarioVistoUsmEmpleo> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.UsuarioVistoUsmEmpleo}
     *
     * @param idEmpresaBuscar Id de {@link crm.entities.Empresa} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE UsuarioVistoUsmEmpleo AS u SET u.idUsuario = :idUsuarioSetear WHERE u.idEmpresa = :idEmpresaBuscar AND u.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idEmpresaBuscar") Long idEmpresaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.UsuarioVistoUsmEmpleo} segun los id especificados como parametro
     *
     * @param idEmpresaBuscar Id del {@link crm.entities.Empresa} asociado a {@link crm.entities.UsuarioVistoUsmEmpleo}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.UsuarioVistoUsmEmpleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM UsuarioVistoUsmEmpleo AS u WHERE u.idEmpresa = :idEmpresaBuscar AND u.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("idEmpresaBuscar") Long idEmpresaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Elimina un {@link crm.entities.UsuarioVistoUsmEmpleo} segun la id del usuario especificado como parametro
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.UsuarioVistoUsmEmpleo}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM UsuarioVistoUsmEmpleo AS u WHERE u.idUsuario = :idUsuarioBuscar")
    void eliminarPorIdUsuario(@Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Retorna una List con {@link crm.entities.UsuarioVistoUsmEmpleo} que posean un id de empresa
     * igual al parametro que se le entregue
     *
     * @param idEmpresa id de la {@link crm.entities.Empresa} buscada
     *
     * @return List con {@link crm.entities.UsuarioVistoUsmEmpleo} asociados a una {@link crm.entities.Empresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT u FROM UsuarioVistoUsmEmpleo AS u WHERE u.idEmpresa = :idEmpresa ")
    List<UsuarioVistoUsmEmpleo> buscarUsuarioVistoUsmempleoPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);
}
