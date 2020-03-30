package crm.repositories;

import crm.entities.CategoriaAsesoriaUsuario;
import crm.entities.TestPsicologicoExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TestPsicologicoExalumno}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TestPsicologicoExalumnoRepository extends CrudRepository<TestPsicologicoExalumno, Integer> {




    /**
     * Retorna una List con {@link crm.entities.TestPsicologicoExalumno} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.TestPsicologicoExalumno} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT t FROM TestPsicologicoExalumno AS t WHERE t.usuario.id = :idUsuario ")
    List<TestPsicologicoExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.TestPsicologicoExalumno}
     *
     * @param idTestPsicologicoExalumnoBuscar Id del {@link crm.entities.TestPsicologicoExalumno} al que se le desea
     *                                         setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE TestPsicologicoExalumno AS t SET t.usuario.id = :idUsuarioSetear WHERE t.id = :idTestPsicologicoExalumnoBuscar")
    void actualizarUsuarioId(@Param("idTestPsicologicoExalumnoBuscar") Integer idTestPsicologicoExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.TestPsicologicoExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.TestPsicologicoExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM TestPsicologicoExalumno AS t WHERE t.id = :idBuscar")
    void eliminar(@Param("idBuscar") Integer idBuscar);

}
