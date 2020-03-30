package crm.repositories;

import crm.entities.ArchivosAdjuntos;
import crm.entities.CampanaExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CampanaExalumno}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CampanaExalumnoRepository extends CrudRepository<CampanaExalumno, Long> {




    /**
     * Retorna una List con {@link crm.entities.CampanaExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.CampanaExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM CampanaExalumno AS c WHERE c.usuario.id = :idUsuario ")
    List<CampanaExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.CampanaExalumno}
     *
     * @param idCampanaExalumnoBuscar Id del {@link crm.entities.CampanaExalumno} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE CampanaExalumno AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idCampanaExalumnoBuscar")
    void actualizarUsuarioId(@Param("idCampanaExalumnoBuscar") Long idCampanaExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.CampanaExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.CampanaExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CampanaExalumno AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    /**
     * Elimina un {@link crm.entities.CampanaExalumno} segun la id especificada como parametro
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.CampanaExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CampanaExalumno AS c WHERE c.usuario.id = :idUsuarioBuscar")
    void eliminarPorIdUsuario(@Param("idUsuarioBuscar") Long idUsuarioBuscar);





}
