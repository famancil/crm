package crm.repositories;

import crm.entities.CampanaExalumno;
import crm.entities.PaginaExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PaginaExalumno}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PaginaExalumnoRepository extends CrudRepository<PaginaExalumno, Long> {




    /**
     * Retorna una List con {@link crm.entities.PaginaExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PaginaExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PaginaExalumno AS p WHERE p.usuario.id = :idUsuario ")
    List<PaginaExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PaginaExalumno}
     *
     * @param idPaginaExalumnoBuscar Id del {@link crm.entities.PaginaExalumno} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PaginaExalumno AS p SET p.usuario.id = :idUsuarioSetear WHERE p.id = :idPaginaExalumnoBuscar")
    void actualizarUsuarioId(@Param("idPaginaExalumnoBuscar") Long idPaginaExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.PaginaExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.PaginaExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PaginaExalumno AS p WHERE p.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);





}
