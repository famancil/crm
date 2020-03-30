package crm.repositories;

import crm.entities.Asesor;
import crm.entities.CorreosValidados;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Asesor}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CorreosValidadosRepository extends CrudRepository<CorreosValidados, Long> {




    /**
     * Retorna una List con {@link crm.entities.CorreosValidados} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CorreosValidados} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CorreosValidados AS c WHERE c.idUsuario = :idUsuario ")
    List<CorreosValidados> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CorreosValidados}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CorreosValidados AS c SET c.idUsuario = :idSetear WHERE c.idUsuario = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear);



    /**
     * Elimina un {@link crm.entities.CorreosValidados} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.CorreosValidados}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CorreosValidados AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);




}
