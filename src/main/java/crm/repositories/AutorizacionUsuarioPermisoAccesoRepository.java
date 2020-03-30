package crm.repositories;

import crm.entities.AutorizacionUsuario;
import crm.entities.AutorizacionUsuarioPermisoAcceso;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AutorizacionUsuarioPermisoAcceso}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface AutorizacionUsuarioPermisoAccesoRepository extends CrudRepository<AutorizacionUsuarioPermisoAcceso, Long>{

    /**
     * Retorna una List con {@link crm.entities.AutorizacionUsuarioPermisoAcceso} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param id id del {@link crm.entities.AutorizacionUsuarioPermisoAcceso} buscado
     *
     * @return List con {@link crm.entities.AutorizacionUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AutorizacionUsuarioPermisoAcceso AS a WHERE a.autorizacionUsuarioId = :id ")
    List<AutorizacionUsuarioPermisoAcceso> getByIdAutorizacionUsuarioPermisoAcceso(@Param("id")Long id);

    /**
     * Elimina un {@link crm.entities.AutorizacionUsuarioPermisoAcceso} segun la id del compromiso especificada como parametro.
     *
     * @param idAutorizacionUsuario Id del {@link crm.entities.AutorizacionUsuario} asociado a {@link crm.entities.AutorizacionUsuarioPermisoAcceso}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query("DELETE FROM AutorizacionUsuarioPermisoAcceso AS a WHERE a.autorizacionUsuarioId = :idAutorizacionUsuario")
    void eliminar(@Param("idAutorizacionUsuario") Long idAutorizacionUsuario);
}
