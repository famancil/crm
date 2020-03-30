package crm.repositories;

import crm.entities.AporteSocio;
import crm.entities.AporteSocioPK;
import crm.entities.CompetenciaExalumno;
import crm.entities.CompetenciaExalumnoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteSocio}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface AporteSocioRepository extends CrudRepository<AporteSocio, AporteSocioPK> {



    /**
     * Retorna una List con {@link crm.entities.AporteSocio} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AporteSocio} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AporteSocio AS a WHERE a.idUsuario = :idUsuario ")
    List<AporteSocio> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AporteSocio}
     *
     * @param idCompromisoSocioBuscar Id de {@link crm.entities.CompromisoSocio} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param fechaBuscar Fecha del {@link crm.entities.AporteSocio} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AporteSocio AS a SET a.idUsuario = :idUsuarioSetear WHERE a.idCompromisoSocio = :idCompromisoSocioBuscar AND a.idUsuario = :idUsuarioBuscar AND a.fecha = :fechaBuscar ")
    void actualizarUsuarioId(@Param("idCompromisoSocioBuscar") Long idCompromisoSocioBuscar, @Param("fechaBuscar") Date fechaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.AporteSocio} segun la id pasada por parametro.
     *
     * @param idCampanaBuscar Id del {@link crm.entities.CampanaExalumno} asociado a {@link crm.entities.AporteSocio}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AporteSocio AS a WHERE a.campanaExalumno.id = :idCampanaBuscar")
    void eliminarPorIdCampana(@Param("idCampanaBuscar") Long idCampanaBuscar);


    /**
     * Elimina un {@link crm.entities.AporteSocio} segun los id especificados como parametro
     *
     * @param idCompromisoSocioBuscar Id del {@link crm.entities.CompromisoSocio} asociado a {@link crm.entities.AporteSocio}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AporteSocio AS a WHERE a.compromisoSocio.id = :idCompromisoSocioBuscar")
    void eliminarPorIdCompromiso(@Param("idCompromisoSocioBuscar") Long idCompromisoSocioBuscar);


    /**
     * Elimina un {@link crm.entities.AporteSocio} segun los id especificados como parametro
     *
     * @param idCompromisoSocioBuscar Id del {@link crm.entities.CompromisoSocio} asociado a {@link crm.entities.AporteSocio}
     * @param fechaBuscar Fecha de {@link crm.entities.AporteSocio}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.AporteSocio}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AporteSocio AS a WHERE a.idCompromisoSocio = :idCompromisoSocioBuscar AND a.idUsuario = :idUsuarioBuscar AND a.fecha = :fechaBuscar ")
    void eliminar(@Param("idCompromisoSocioBuscar") Long idCompromisoSocioBuscar, @Param("fechaBuscar") Date fechaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

}

