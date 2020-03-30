package crm.repositories;

import crm.entities.PostulacionUsuarioAsesoria;
import crm.entities.PostulacionUsuarioAsesoriaPK;
import crm.entities.PostulanteFavorito;
import crm.entities.PostulanteFavoritoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionUsuarioAsesoria}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PostulacionUsuarioAsesoriaRepository extends CrudRepository<PostulacionUsuarioAsesoria, PostulacionUsuarioAsesoriaPK> {



    /**
     * Retorna una List con {@link crm.entities.PostulacionUsuarioAsesoria} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PostulacionUsuarioAsesoria} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionUsuarioAsesoria AS p WHERE p.idUsuario = :idUsuario ")
    List<PostulacionUsuarioAsesoria> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PostulacionUsuarioAsesoria}
     *
     * @param idOfertaAsesoriaBuscar Id de {@link crm.entities.OfertaAsesoria} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PostulacionUsuarioAsesoria AS p SET p.idUsuario = :idUsuarioSetear WHERE p.idOfertaAsesoria = :idOfertaAsesoriaBuscar AND p.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idOfertaAsesoriaBuscar") Long idOfertaAsesoriaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.PostulacionUsuarioAsesoria} segun los id especificados como parametro
     *
     * @param idOfertaAsesoriaBuscar Id del {@link crm.entities.OfertaAsesoria} asociado a {@link crm.entities.PostulacionUsuarioAsesoria}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulacionUsuarioAsesoria}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulacionUsuarioAsesoria AS p WHERE p.idOfertaAsesoria = :idOfertaAsesoriaBuscar AND p.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("idOfertaAsesoriaBuscar") Long idOfertaAsesoriaBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
