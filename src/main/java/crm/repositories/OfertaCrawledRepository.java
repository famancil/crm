package crm.repositories;

import crm.entities.OfertaCrawled;
import crm.entities.TestPsicologicoExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaCrawled}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface OfertaCrawledRepository extends CrudRepository<OfertaCrawled, Long> {




    /**
     * Retorna una List con {@link crm.entities.OfertaCrawled} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.OfertaCrawled} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o FROM OfertaCrawled AS o WHERE o.usuario.id = :idUsuario ")
    List<OfertaCrawled> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.OfertaCrawled}
     *
     * @param idOfertaCrawledBuscar Id del {@link crm.entities.OfertaCrawled} al que se le desea
     *                                         setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE OfertaCrawled AS o SET o.usuario.id = :idUsuarioSetear WHERE o.id = :idOfertaCrawledBuscar")
    void actualizarUsuarioId(@Param("idOfertaCrawledBuscar") Long idOfertaCrawledBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.OfertaCrawled} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.OfertaCrawled}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM OfertaCrawled AS o WHERE o.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
