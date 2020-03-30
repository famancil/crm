package crm.repositories;

import crm.entities.PostulacionArchivosAdjuntos;
import crm.entities.PostulacionArchivosAdjuntosPK;
import crm.entities.RespuestaPreguntaOfertaLaboralExalumno;
import crm.entities.RespuestaPreguntaOfertaLaboralExalumnoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionArchivosAdjuntos}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PostulacionArchivosAdjuntosRepository extends CrudRepository<PostulacionArchivosAdjuntos, PostulacionArchivosAdjuntosPK> {



    /**
     * Retorna una List con {@link crm.entities.PostulacionArchivosAdjuntos} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionArchivosAdjuntos AS p WHERE p.idUsuario = :idUsuario ")
    List<PostulacionArchivosAdjuntos> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.PostulacionArchivosAdjuntos} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     *
     * @return List con {@link crm.entities.PostulacionArchivosAdjuntos} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionArchivosAdjuntos AS p WHERE p.idUsuario = :idUsuario AND p.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleo")
    List<PostulacionArchivosAdjuntos> buscarPorIdUsuarioYIdOfertaLaboral(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo);




    /**
     * Retorna una {@link crm.entities.PostulacionArchivosAdjuntos} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     * @param idArchivoAdjunto id del {@link crm.entities.PreguntaOfertaLaboralUsmEmpleo} buscado
     *
     * @return List con {@link crm.entities.ArchivosAdjuntos} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionArchivosAdjuntos AS p WHERE p.idUsuario = :idUsuario AND p.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleo AND p.idArchivoAdjunto = :idArchivoAdjunto")
    PostulacionArchivosAdjuntos buscarPorIdUsuarioYIdOfertaLaboralYIdArchivoAdjunto(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo, @Param("idArchivoAdjunto") Long idArchivoAdjunto);




    /**
     * Elimina un {@link crm.entities.PostulacionArchivosAdjuntos} segun los id especificados como parametro
     *
     * @param idUsuario Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulacionArchivosAdjuntos}
     * @param idOfertaLaboralUsmempleo Id del {@link crm.entities.OfertaLaboralUsmempleo} asociado a {@link crm.entities.PostulacionArchivosAdjuntos}
     * @param idArchivoAdjunto Id del {@link crm.entities.ArchivosAdjuntos} asociado a {@link crm.entities.PostulacionArchivosAdjuntos}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulacionArchivosAdjuntos AS p WHERE p.idUsuario = :idUsuario AND p.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleo AND p.idArchivoAdjunto = :idArchivoAdjunto")
    void eliminar(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo, @Param("idArchivoAdjunto") Long idArchivoAdjunto);

    /**
     * Elimina un {@link crm.entities.PostulacionArchivosAdjuntos} segun el id del usuario.
     *
     * @param idUsuario Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulacionArchivosAdjuntos}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulacionArchivosAdjuntos AS p WHERE p.idUsuario = :idUsuario")
    void eliminarPorIdUsuario(@Param("idUsuario") Long idUsuario);
}
