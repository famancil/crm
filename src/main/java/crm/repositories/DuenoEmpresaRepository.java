package crm.repositories;

import crm.entities.DuenoEmpresa;
import crm.entities.DuenoEmpresaPK;
import crm.entities.UsuarioVistoUsmEmpleo;
import crm.entities.UsuarioVistoUsmEmpleoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.DuenoEmpresa}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface DuenoEmpresaRepository extends CrudRepository<DuenoEmpresa, DuenoEmpresaPK> {



    /**
     * Retorna una List con {@link crm.entities.DuenoEmpresa} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.DuenoEmpresa} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT d FROM DuenoEmpresa AS d WHERE d.usuarioId = :idUsuario ")
    List<DuenoEmpresa> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.DuenoEmpresa}
     *
     * @param idEmpresaBuscar Id de {@link crm.entities.Empresa} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE DuenoEmpresa AS d SET d.usuarioId = :idUsuarioSetear WHERE d.empresaId = :idEmpresaBuscar AND d.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idEmpresaBuscar") Long idEmpresaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.DuenoEmpresa} segun los id especificados como parametro
     *
     * @param idEmpresaBuscar Id del {@link crm.entities.Empresa} asociado a {@link crm.entities.DuenoEmpresa}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.DuenoEmpresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM DuenoEmpresa AS d WHERE d.empresaId = :idEmpresaBuscar AND d.usuarioId = :idUsuarioBuscar")
    void eliminar(@Param("idEmpresaBuscar") Long idEmpresaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Retorna una List con {@link crm.entities.DuenoEmpresa} que posean un idEmpresa
     * igual al parametro que se le entregue
     *
     * @param idEmpresa id del {@link crm.entities.DuenoEmpresa} buscado
     *
     * @return List con {@link crm.entities.DuenoEmpresa} asociados a una {@link crm.entities.Empresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT d FROM DuenoEmpresa AS d WHERE d.empresaId = :idEmpresa ")
    List<DuenoEmpresa> buscarPorIdEmpresa(@Param("idEmpresa") Long  idEmpresa);
}
