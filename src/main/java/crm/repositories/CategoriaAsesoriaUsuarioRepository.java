package crm.repositories;

import crm.entities.CategoriaAsesoriaUsuario;
import crm.entities.PagoAsesoria;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CategoriaAsesoriaUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CategoriaAsesoriaUsuarioRepository extends CrudRepository<CategoriaAsesoriaUsuario, Long> {




    /**
     * Retorna una List con {@link crm.entities.CategoriaAsesoriaUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CategoriaAsesoriaUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CategoriaAsesoriaUsuario AS c WHERE c.usuario.id = :idUsuario ")
    List<CategoriaAsesoriaUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CategoriaAsesoriaUsuario}
     *
     * @param idCategoriaAsesoriaUsuarioBuscar Id del {@link crm.entities.CategoriaAsesoriaUsuario} al que se le desea
     *                                         setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CategoriaAsesoriaUsuario AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idCategoriaAsesoriaUsuarioBuscar")
    void actualizarUsuarioId(@Param("idCategoriaAsesoriaUsuarioBuscar") Long idCategoriaAsesoriaUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.CategoriaAsesoriaUsuario} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.CategoriaAsesoriaUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CategoriaAsesoriaUsuario AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
