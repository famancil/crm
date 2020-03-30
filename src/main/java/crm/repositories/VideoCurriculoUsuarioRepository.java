package crm.repositories;

import crm.entities.VideoCurriculoUsuario;
import crm.entities.VideoEntrevistaUsmEmpleo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.VideoCurriculoUsuario}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface VideoCurriculoUsuarioRepository extends CrudRepository<VideoCurriculoUsuario, Long> {




    /**
     * Retorna una List con {@link crm.entities.VideoCurriculoUsuario} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.VideoCurriculoUsuario} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT v FROM VideoCurriculoUsuario AS v WHERE v.usuario.id = :idUsuario ")
    List<VideoCurriculoUsuario> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.VideoCurriculoUsuario}
     *
     * @param idVideoCurriculoUsuarioBuscar Id del {@link crm.entities.VideoCurriculoUsuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE VideoCurriculoUsuario AS v SET v.usuario.id = :idUsuarioSetear WHERE v.id = :idVideoCurriculoUsuarioBuscar")
    void actualizarUsuarioId(@Param("idVideoCurriculoUsuarioBuscar") Long idVideoCurriculoUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.VideoCurriculoUsuario} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.VideoCurriculoUsuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM VideoCurriculoUsuario AS v WHERE v.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Cuenta la cantidad de video curriculos publicados
     *
     * @return {@link java.lang.Long}
     */
    @Query("SELECT COUNT(v) FROM VideoCurriculoUsuario AS v WHERE v.publicado = TRUE")
    Long contarVcvPublicados();

    /**
     * Cuenta la cantidad de video curriculos por publicar
     *
     * @return {@link java.lang.Long}
     */
    @Query("SELECT COUNT(v) FROM VideoCurriculoUsuario AS v WHERE v.publicado = FALSE")
    Long contarVcvPorPublicar();

}
