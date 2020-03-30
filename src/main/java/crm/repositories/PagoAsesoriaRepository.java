package crm.repositories;

import crm.entities.FiltroOfertaLaboral;
import crm.entities.PagoAsesoria;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PagoAsesoria}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PagoAsesoriaRepository extends CrudRepository<PagoAsesoria, Long> {




    /**
     * Retorna una List con {@link crm.entities.PagoAsesoria} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PagoAsesoria} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PagoAsesoria AS p WHERE p.usuario.id = :idUsuario ")
    List<PagoAsesoria> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PagoAsesoria}
     *
     * @param idPagoAsesoriaBuscar Id del {@link crm.entities.PagoAsesoria} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PagoAsesoria AS p SET p.usuario.id = :idUsuarioSetear WHERE p.id = :idPagoAsesoriaBuscar")
    void actualizarUsuarioId(@Param("idPagoAsesoriaBuscar") Long idPagoAsesoriaBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.PagoAsesoria} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.PagoAsesoria}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PagoAsesoria AS p WHERE p.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
