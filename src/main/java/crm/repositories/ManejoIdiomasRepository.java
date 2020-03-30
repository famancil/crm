package crm.repositories;

import crm.entities.Asesor;
import crm.entities.ManejoIdioma;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ManejoIdioma}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface ManejoIdiomasRepository extends CrudRepository<ManejoIdioma, Long> {




    /**
     * Retorna una List con {@link crm.entities.ManejoIdioma} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.ManejoIdioma} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT m FROM ManejoIdioma AS m WHERE m.usuario.id = :idUsuario ")
    List<ManejoIdioma> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.ManejoIdioma}
     *
     * @param idManejoIdiomaBuscar Id del {@link crm.entities.ManejoIdioma} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE ManejoIdioma AS m SET m.usuario.id = :idUsuarioSetear WHERE m.id = :idManejoIdiomaBuscar")
    void actualizarUsuarioId(@Param("idManejoIdiomaBuscar") Long idManejoIdiomaBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.ManejoIdioma} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.ManejoIdioma}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ManejoIdioma AS m WHERE m.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);





}
