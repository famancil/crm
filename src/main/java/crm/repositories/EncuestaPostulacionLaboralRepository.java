package crm.repositories;

import crm.entities.EncuestaPostulacionLaboral;
import crm.entities.EncuestaPostulacionLaboralPK;
import crm.entities.PostulanteFavorito;
import crm.entities.PostulanteFavoritoPK;
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
public interface EncuestaPostulacionLaboralRepository extends CrudRepository<EncuestaPostulacionLaboral, EncuestaPostulacionLaboralPK> {



    /**
     * Retorna una List con {@link crm.entities.EncuestaPostulacionLaboral} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.EncuestaPostulacionLaboral} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT e FROM EncuestaPostulacionLaboral AS e WHERE e.idUsuario = :idUsuario ")
    List<EncuestaPostulacionLaboral> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.EncuestaPostulacionLaboral} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     *
     * @return List con {@link crm.entities.EncuestaPostulacionLaboral} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT e FROM EncuestaPostulacionLaboral AS e WHERE e.idUsuario = :idUsuario AND e.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleo")
    EncuestaPostulacionLaboral buscarPorIdUsuarioYIdOfertaLaboral(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.EncuestaPostulacionLaboral}
     *
     * @param idOfertaLaboralUsmempleoBuscar Id de {@link crm.entities.OfertaLaboralUsmempleo} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE EncuestaPostulacionLaboral AS e SET e.idUsuario = :idUsuarioSetear WHERE e.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleoBuscar AND e.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idOfertaLaboralUsmempleoBuscar") Long idOfertaLaboralUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.EncuestaPostulacionLaboral} segun los id especificados como parametro
     *
     * @param idOfertaLaboralUsmempleoBuscar Id del {@link crm.entities.OfertaLaboralUsmempleo} asociado a {@link crm.entities.EncuestaPostulacionLaboral}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.EncuestaPostulacionLaboral}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM EncuestaPostulacionLaboral AS e WHERE e.idOfertaLaboralUsmempleo = :idOfertaLaboralUsmempleoBuscar AND e.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("idOfertaLaboralUsmempleoBuscar") Long idOfertaLaboralUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Retorna la cantidad de encuestas de postulaciones laborales respondidas
     *
     * @return {@link java.lang.Long}
     */
    @Query("SELECT COUNT(e) FROM EncuestaPostulacionLaboral AS e WHERE e.opina = TRUE")
    Long contarEncuestasResponidas();

}
