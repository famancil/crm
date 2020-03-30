package crm.repositories;

import crm.entities.AccesoSistema;
import crm.entities.AccesoSistemaPK;
import crm.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AccesoSistema}.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface AccesoSistemaRepository extends CrudRepository<AccesoSistema, AccesoSistemaPK> {

    /**
     * Retorna un listado de todos los accesos que posee un {@link crm.entities.Usuario} a los distintos
     * {@link crm.entities.Sistema} disponibles en la red de exalumnos.
     *
     * @param usuario usuario al que se le desean listar los accesos.
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.AccesoSistema}.
     */
    List<AccesoSistema> findByUsuario(Usuario usuario);



    /**
     * Retorna una List con {@link crm.entities.AccesoSistema} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AccesoSistema} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AccesoSistema AS a WHERE a.compositeId.usuarioId = :idUsuario ")
    List<AccesoSistema> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AccesoSistema}
     *
     * @param idSistemaBuscar Id de {@link crm.entities.Sistema} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AccesoSistema AS a SET a.compositeId.usuarioId = :idUsuarioSetear WHERE a.compositeId.codigoSistema = :idSistemaBuscar AND a.compositeId.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idSistemaBuscar") Short idSistemaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Elimina un {@link crm.entities.AccesoSistema} segun los id especificados como parametro
     *
     * @param idSistemaBuscar Id del {@link crm.entities.Sistema} asociado a {@link crm.entities.AccesoSistema}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.AccesoSistema}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AccesoSistema AS a WHERE a.compositeId.codigoSistema = :idSistemaBuscar AND a.compositeId.usuarioId = :idUsuarioBuscar")
    void eliminar(@Param("idSistemaBuscar") Short idSistemaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
