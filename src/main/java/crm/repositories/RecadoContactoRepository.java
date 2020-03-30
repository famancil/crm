package crm.repositories;

import crm.entities.RecadoContacto;
import crm.entities.RecadoContactoPK;
import crm.entities.StickynotesAexa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.RecadoContacto}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface RecadoContactoRepository extends CrudRepository<RecadoContacto, RecadoContactoPK> {



    /**
     * Retorna una List con {@link crm.entities.RecadoContacto} que posean un usuarioId (quien recibe el recado)
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} (quien realiza el stickynote) buscado
     *
     * @return List con {@link crm.entities.RecadoContacto} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RecadoContacto AS r WHERE r.usuarioId = :idUsuario ")
    List<RecadoContacto> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.RecadoContacto} que posean un usuarioId (quien envia el recado)
     * igual al parametro que se le entregue
     *
     * @param idUsuUsuario id del {@link crm.entities.Usuario} (quien realiza el stickynote)  buscado
     *
     * @return List con {@link crm.entities.RecadoContacto} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RecadoContacto AS r WHERE r.usuUsuario.id = :idUsuUsuario ")
    List<RecadoContacto> buscarPorIdUsuUsuario(@Param("idUsuUsuario") Long idUsuUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} (a quien va dirigido el recado) asociado de un {@link crm.entities.RecadoContacto}
     *
     * @param fechaBuscar Fecha del {@link crm.entities.RecadoContacto} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE RecadoContacto AS r SET r.usuario.id = :idUsuarioSetear WHERE r.fecha = :fechaBuscar AND r.usuario.id = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("fechaBuscar") Date fechaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);









    /**
     * Actualiza el {@link crm.entities.Usuario} (quien envia el recado) asociado de un {@link crm.entities.RecadoContacto}
     *
     * @param fechaBuscar Fecha del {@link crm.entities.RecadoContacto} al que se le desea setear el valor
     * @param idUsuUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE RecadoContacto AS r SET r.usuUsuario.id = :idUsuarioSetear WHERE r.fecha = :fechaBuscar AND r.usuUsuario.id = :idUsuUsuarioBuscar")
    void actualizarUsuUsuarioId(@Param("fechaBuscar") Date fechaBuscar, @Param("idUsuUsuarioBuscar") Long idUsuUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.RecadoContacto} segun los id especificados como parametro
     *
     * @param fechaBuscar Fecha de {@link crm.entities.RecadoContacto}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RecadoContacto}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RecadoContacto AS r WHERE r.fecha = :fechaBuscar AND r.usuario.id = :idUsuarioBuscar")
    void eliminar(@Param("fechaBuscar") Date fechaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Elimina un {@link crm.entities.RecadoContacto} segun la id  del usuario especificado como parametro
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RecadoContacto}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RecadoContacto AS r WHERE r.usuUsuario.id = :idUsuarioBuscar")
    void eliminarPorIdUsuUsuario(@Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
