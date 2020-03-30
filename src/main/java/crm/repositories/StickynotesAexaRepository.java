package crm.repositories;

import crm.entities.PostulacionOfertaLaboralUsmempleo;
import crm.entities.PostulacionOfertaLaboralUsmempleoPK;
import crm.entities.StickynotesAexa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.StickynotesAexa}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface StickynotesAexaRepository extends CrudRepository<StickynotesAexa, Integer> {



    /**
     * Retorna una List con {@link crm.entities.StickynotesAexa} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} (quien realiza el stickynote) buscado
     *
     * @return List con {@link crm.entities.StickynotesAexa} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT s FROM StickynotesAexa AS s WHERE s.usuario.id = :idUsuario ")
    List<StickynotesAexa> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.StickynotesAexa} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuUsuario id del {@link crm.entities.Usuario} (quien realiza el stickynote)  buscado
     *
     * @return List con {@link crm.entities.StickynotesAexa} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT s FROM StickynotesAexa AS s WHERE s.usuUsuario.id = :idUsuUsuario ")
    List<StickynotesAexa> buscarPorIdUsuUsuario(@Param("idUsuUsuario") Long idUsuUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} (a quien va dirigido el stickynote) asociado de un {@link crm.entities.StickynotesAexa}
     *
     * @param idStickynotesAexaBuscar Id del {@link crm.entities.StickynotesAexa} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE StickynotesAexa AS s SET s.usuario.id = :idUsuarioSetear WHERE s.id = :idStickynotesAexaBuscar")
    void actualizarUsuarioId(@Param("idStickynotesAexaBuscar") Integer idStickynotesAexaBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Actualiza el {@link crm.entities.Usuario} (a quien va dirigido el stickynote) asociado de un {@link crm.entities.StickynotesAexa}
     *
     * @param idStickynotesAexaBuscar Id del {@link crm.entities.StickynotesAexa} al que se le desea setear el valor
     * @param idUsuUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE StickynotesAexa AS s SET s.usuUsuario.id = :idUsuUsuarioSetear WHERE s.id = :idStickynotesAexaBuscar")
    void actualizarUsuUsuarioId(@Param("idStickynotesAexaBuscar") Integer idStickynotesAexaBuscar, @Param("idUsuUsuarioSetear") Long idUsuUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.StickynotesAexa} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.StickynotesAexa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM StickynotesAexa AS s WHERE s.id = :idBuscar")
    void eliminar(@Param("idBuscar") Integer idBuscar);

}
