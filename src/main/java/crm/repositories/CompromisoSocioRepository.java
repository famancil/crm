package crm.repositories;

import crm.entities.CompromisoSocio;
import crm.entities.ManejoIdioma;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompromisoSocio}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface CompromisoSocioRepository extends CrudRepository<CompromisoSocio, Long> {




    /**
     * Retorna una List con {@link crm.entities.CompromisoSocio} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CompromisoSocio} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CompromisoSocio AS c WHERE c.usuario.id = :idUsuario ")
    List<CompromisoSocio> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);

    /**
     * Retorna una List con {@link crm.entities.CompromisoSocio} que posean un campanaId
     * igual al parametro que se le entregue
     *
     * @param idCampana id del {@link crm.entities.CampanaExalumno} buscado
     *
     * @return List con {@link crm.entities.CompromisoSocio} asociados a un {@link crm.entities.CampanaExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CompromisoSocio AS c WHERE c.campanaExalumno.id = :idCampana ")
    List<CompromisoSocio> buscarPorIdCampana(@Param("idCampana") Long idCampana);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CompromisoSocio}
     *
     * @param idCompromisoSocioBuscar Id del {@link crm.entities.CompromisoSocio} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CompromisoSocio AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idCompromisoSocioBuscar")
    void actualizarUsuarioId(@Param("idCompromisoSocioBuscar") Long idCompromisoSocioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.CompromisoSocio} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.CompromisoSocio}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CompromisoSocio AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Elimina un {@link crm.entities.AporteSocio} segun la id pasada por parametro.
     *
     * @param idCampanaBuscar Id del {@link crm.entities.CampanaExalumno} asociado a {@link crm.entities.AporteSocio}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CompromisoSocio AS a WHERE a.campanaExalumno.id = :idCampanaBuscar")
    void eliminarPorIdCampana(@Param("idCampanaBuscar") Long idCampanaBuscar);

    /**
     * Elimina un {@link crm.entities.CompromisoSocio} segun la id pasada por parametro.
     *
     * @param idUsuarioCompromiso Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.CompromisoSocio}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CompromisoSocio AS a WHERE a.usuario.id = :idUsuarioCompromiso")
    void eliminarPorIdUsuario(@Param("idUsuarioCompromiso") Long idUsuarioCompromiso);
}
