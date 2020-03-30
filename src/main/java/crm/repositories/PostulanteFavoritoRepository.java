package crm.repositories;

import crm.entities.PostulanteFavorito;
import crm.entities.PostulanteFavoritoPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulanteFavorito}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PostulanteFavoritoRepository extends CrudRepository<PostulanteFavorito, PostulanteFavoritoPK> {



    /**
     * Retorna una List con {@link crm.entities.PostulanteFavorito} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PostulanteFavorito} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulanteFavorito AS p WHERE p.idUsuario = :idUsuario ")
    List<PostulanteFavorito> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PostulanteFavorito}
     *
     * @param idUsuarioEmpresaUsmempleoBuscar Id de {@link crm.entities.UsuarioEmpresaUsmempleo} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PostulanteFavorito AS p SET p.idUsuario = :idUsuarioSetear WHERE p.idUsuarioEmpresaUsmempleo = :idUsuarioEmpresaUsmempleoBuscar AND p.idUsuario = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idUsuarioEmpresaUsmempleoBuscar") Long idUsuarioEmpresaUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.PostulanteFavorito} segun los id especificados como parametro
     *
     * @param idUsuarioEmpresaUsmempleoBuscar Id del {@link crm.entities.UsuarioEmpresaUsmempleo} asociado a {@link crm.entities.PostulanteFavorito}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulanteFavorito}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulanteFavorito AS p WHERE p.idUsuarioEmpresaUsmempleo = :idUsuarioEmpresaUsmempleoBuscar AND p.idUsuario = :idUsuarioBuscar")
    void eliminar(@Param("idUsuarioEmpresaUsmempleoBuscar") Long idUsuarioEmpresaUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);
}
