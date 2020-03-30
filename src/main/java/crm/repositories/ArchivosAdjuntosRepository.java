package crm.repositories;

import crm.entities.ArchivosAdjuntos;
import crm.entities.ManejoIdioma;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ArchivosAdjuntos}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface ArchivosAdjuntosRepository extends CrudRepository<ArchivosAdjuntos, Long> {




    /**
     * Retorna una List con {@link crm.entities.ArchivosAdjuntos} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.ArchivosAdjuntos} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM ArchivosAdjuntos AS a WHERE a.usuario.id = :idUsuario ")
    List<ArchivosAdjuntos> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.ArchivosAdjuntos}
     *
     * @param idArchivosAdjuntosBuscar Id del {@link crm.entities.ArchivosAdjuntos} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE ArchivosAdjuntos AS a SET a.usuario.id = :idUsuarioSetear WHERE a.id = :idArchivosAdjuntosBuscar")
    void actualizarUsuarioId(@Param("idArchivosAdjuntosBuscar") Long idArchivosAdjuntosBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.ArchivosAdjuntos} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.ArchivosAdjuntos}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ArchivosAdjuntos AS a WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
