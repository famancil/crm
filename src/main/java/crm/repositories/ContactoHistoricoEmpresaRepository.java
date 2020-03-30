package crm.repositories;

import crm.entities.ContactoHistoricoEmpresa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ContactoHistoricoEmpresa}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface ContactoHistoricoEmpresaRepository extends CrudRepository<ContactoHistoricoEmpresa, Long> {




    /**
     * Retorna una List con {@link crm.entities.ContactoHistoricoEmpresa} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.ContactoHistoricoEmpresa} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c FROM ContactoHistoricoEmpresa AS c WHERE c.usuario.id = :idUsuario ")
    List<ContactoHistoricoEmpresa> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.ContactoHistoricoEmpresa}
     *
     * @param idContactoHistoricoEmpresaBuscar Id del {@link crm.entities.ContactoHistoricoEmpresa} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE ContactoHistoricoEmpresa AS c SET c.usuario.id = :idUsuarioSetear WHERE c.id = :idContactoHistoricoEmpresaBuscar")
    void actualizarUsuarioId(@Param("idContactoHistoricoEmpresaBuscar") Long idContactoHistoricoEmpresaBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.ContactoHistoricoEmpresa} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.ContactoHistoricoEmpresa}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ContactoHistoricoEmpresa AS c WHERE c.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

}
