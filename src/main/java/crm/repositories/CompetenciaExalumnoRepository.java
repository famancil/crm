package crm.repositories;

import crm.entities.CompetenciaExalumno;
import crm.entities.CompetenciaExalumnoPK;
import crm.entities.PostulacionOfertaLaboralUsmempleo;
import crm.entities.PostulacionOfertaLaboralUsmempleoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompetenciaExalumno}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CompetenciaExalumnoRepository extends CrudRepository<CompetenciaExalumno, CompetenciaExalumnoPK> {



    /**
     * Retorna una List con {@link crm.entities.CompetenciaExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CompetenciaExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CompetenciaExalumno AS c WHERE c.usuarioId = :idUsuario ")
    List<CompetenciaExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CompetenciaExalumno}
     *
     * @param idCompetenciaExalumnoBuscar Id de {@link crm.entities.CompetenciaExalumno} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CompetenciaExalumno AS c SET c.usuarioId = :idUsuarioSetear WHERE c.nivelCompetenciaUsmempleoId = :idCompetenciaExalumnoBuscar AND c.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idCompetenciaExalumnoBuscar") Long idCompetenciaExalumnoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Elimina un {@link crm.entities.CompetenciaExalumno} segun los id especificados como parametro
     *
     * @param idCompetenciaExalumnoBuscar Id del {@link crm.entities.NivelCompetenciaUsmempleo} asociado a {@link crm.entities.CompetenciaExalumno}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.CompetenciaExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CompetenciaExalumno AS c WHERE c.nivelCompetenciaUsmempleoId = :idCompetenciaExalumnoBuscar AND c.usuarioId = :idUsuarioBuscar")
    void eliminar(@Param("idCompetenciaExalumnoBuscar") Long idCompetenciaExalumnoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
