package crm.repositories;

import crm.entities.ManejoIdioma;
import crm.entities.OperadorContabilidad;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link OperadorContabilidad}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface OperadorContabilidadRepository extends CrudRepository<OperadorContabilidad, Long> {



    /**
     * Retorna una List con {@link crm.entities.OperadorContabilidad} que posean un idUsuario
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} que es manejado por un operador
     *
     * @return List con {@link crm.entities.OperadorContabilidad} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o FROM OperadorContabilidad AS o WHERE o.usuario.id = :idUsuario ")
    List<OperadorContabilidad> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.OperadorContabilidad} que posean un idUsuario
     * igual al parametro que se le entregue
     *
     * @param idUsuarioOperador id del {@link crm.entities.Usuario} que es operador
     *
     * @return List con {@link crm.entities.OperadorContabilidad} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o FROM OperadorContabilidad AS o WHERE o.usuarioOperador.id = :idUsuarioOperador ")
    List<OperadorContabilidad> buscarPorIdUsuarioOperador(@Param("idUsuarioOperador") Long idUsuarioOperador);




    /**
     * Actualiza el {@link crm.entities.Usuario} (que es manejado por un operador) asociado de un {@link crm.entities.OperadorContabilidad}
     *
     * @param idOperadorContabilidadBuscar Id del registro de la tabla {@link crm.entities.OperadorContabilidad} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE OperadorContabilidad AS o SET o.usuario.id = :idUsuarioSetear WHERE o.id = :idOperadorContabilidadBuscar")
    void actualizarIdUsuario(@Param("idOperadorContabilidadBuscar") Long idOperadorContabilidadBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Actualiza el {@link crm.entities.Usuario} (operador) asociado de un {@link crm.entities.OperadorContabilidad}
     *
     * @param idOperadorContabilidadBuscar Id del registro de la tabla {@link crm.entities.OperadorContabilidad} al que se le desea setear el valor
     * @param idUsuarioOperadorSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE OperadorContabilidad AS o SET o.usuarioOperador.id = :idUsuarioOperadorSetear WHERE o.id = :idOperadorContabilidadBuscar")
    void actualizarIdUsuarioOperador(@Param("idOperadorContabilidadBuscar") Integer idOperadorContabilidadBuscar, @Param("idUsuarioOperadorSetear") Long idUsuarioOperadorSetear);



    /**
     * Elimina un {@link crm.entities.OperadorContabilidad} segun el id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.OperadorContabilidad}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM OperadorContabilidad AS o WHERE o.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Elimina un {@link crm.entities.OperadorContabilidad} segun la id  del usuario especificado como parametro
     *
     * @param idUsuario Id del {@link crm.entities.Usuario}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM OperadorContabilidad AS o WHERE o.usuario.id = :idBuscarUsuario")
    void eliminarPorIdUsuario(@Param("idBuscarUsuario") Long idUsuario);

    /**
     * Elimina un {@link crm.entities.OperadorContabilidad} segun la id  del usuario operador especificado como parametro
     *
     * @param idUsuarioOperador Id del {@link crm.entities.Usuario}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM OperadorContabilidad AS o WHERE o.usuarioOperador.id = :idBuscarUsuarioOperador")
    void eliminarPorIdUsuarioOperador(@Param("idBuscarUsuarioOperador") Long idUsuarioOperador);
}
