package crm.repositories;

import crm.entities.TipoCampana;
import crm.entities.Usuario;
import crm.entities.UsuarioApoderado;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioApoderado}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface UsuarioApoderadoRepository extends CrudRepository<UsuarioApoderado, Long> {

    /**
     * Retorna una lista con todas los {@link crm.entities.UsuarioApoderado}
     *
     * @return Instancia {@link crm.entities.UsuarioApoderado}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    UsuarioApoderado findByUsuarioId(Long usuarioId);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.UsuarioApoderado}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE UsuarioApoderado AS ua SET ua.usuarioId = :idSetear WHERE ua.usuarioId = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear);



    /**
     * Elimina un {@link crm.entities.UsuarioApoderado} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.UsuarioApoderado}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM UsuarioApoderado AS ua WHERE ua.usuarioId = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
