package crm.repositories;

import crm.entities.EncuestaPostulacionLaboral;
import crm.entities.EncuestaPostulacionLaboralPK;
import crm.entities.RespuestaPreguntaOfertaLaboralExalumno;
import crm.entities.RespuestaPreguntaOfertaLaboralExalumnoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.EncuestaPostulacionLaboral}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface RespuestaPreguntaOfertaLaboralRepository extends CrudRepository<RespuestaPreguntaOfertaLaboralExalumno, RespuestaPreguntaOfertaLaboralExalumnoPK> {



    /**
     * Retorna una List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RespuestaPreguntaOfertaLaboralExalumno AS r WHERE r.idUsuario = :idUsuario ")
    List<RespuestaPreguntaOfertaLaboralExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     *
     * @return List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RespuestaPreguntaOfertaLaboralExalumno AS r WHERE r.idUsuario = :idUsuario AND r.idOfertaLaboralUsmEmpleo = :idOfertaLaboralUsmempleo")
    List<RespuestaPreguntaOfertaLaboralExalumno> buscarPorIdUsuarioYIdOfertaLaboral(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo);




    /**
     * Retorna una {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     * @param idPreguntaOfertaLaboralUsmEmpleo id del {@link crm.entities.PreguntaOfertaLaboralUsmEmpleo} buscado
     *
     * @return List con {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r FROM RespuestaPreguntaOfertaLaboralExalumno AS r WHERE r.idUsuario = :idUsuario AND r.idOfertaLaboralUsmEmpleo = :idOfertaLaboralUsmempleo AND r.idPreguntaOfertaLaboralUsmEmpleo = :idPreguntaOfertaLaboralUsmEmpleo")
    RespuestaPreguntaOfertaLaboralExalumno buscarPorIdUsuarioYIdOfertaLaboralYIdPregunta(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo, @Param("idPreguntaOfertaLaboralUsmEmpleo") Long idPreguntaOfertaLaboralUsmEmpleo);




    /**
     * Elimina un {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} segun los id especificados como parametro
     *
     * @param idOfertaLaboralUsmempleo Id del {@link crm.entities.OfertaLaboralUsmempleo} asociado a {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     * @param idUsuario Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     * @param idPreguntaOfertaLaboralUsmEmpleo Id del {@link crm.entities.PreguntaOfertaLaboralUsmEmpleo} asociado a {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RespuestaPreguntaOfertaLaboralExalumno AS r WHERE r.idUsuario = :idUsuario AND r.idOfertaLaboralUsmEmpleo = :idOfertaLaboralUsmempleo AND r.idPreguntaOfertaLaboralUsmEmpleo = :idPreguntaOfertaLaboralUsmEmpleo")
    void eliminar(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo, @Param("idPreguntaOfertaLaboralUsmEmpleo") Long idPreguntaOfertaLaboralUsmEmpleo);

    /**
     * Elimina un {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno} segun los id especificados como parametro
     *
     * @param idOfertaLaboralUsmempleo Id del {@link crm.entities.OfertaLaboralUsmempleo} asociado a {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     * @param idUsuario Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.RespuestaPreguntaOfertaLaboralExalumno}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM RespuestaPreguntaOfertaLaboralExalumno AS r WHERE r.idUsuario = :idUsuario AND r.idOfertaLaboralUsmEmpleo = :idOfertaLaboralUsmempleo")
    void eliminarPorIdOfertayIdUsuario(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo);

}
