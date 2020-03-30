package crm.repositories;

import crm.entities.AntecedenteColegio;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteColegio}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface AntecedenteColegioRepository extends CrudRepository<AntecedenteColegio, Long>{

    /**
     * Retorna una lista con los {@link crm.entities.AntecedenteColegio} de una persona.
     *
     * @param idUsuario id del usuario del que se quieren encontrar los antecedentes.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.AntecedenteColegio} de un usuario.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteColegio a WHERE a.usuario.id = :idUsuario ORDER BY a.anoIngreso asc")
    List<AntecedenteColegio> findByColegio(@Param("idUsuario") Long idUsuario);




    /**
     * Retorna una List con {@link crm.entities.AntecedenteColegio} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AntecedenteColegio} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteColegio AS a WHERE a.usuario.id = :idUsuario ")
    List<AntecedenteColegio> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AntecedenteEducacional}
     *
     * @param idAntecedenteColegioBuscar Id del {@link crm.entities.AntecedenteEducacional} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AntecedenteColegio AS a SET a.usuario.id = :idUsuarioSetear WHERE a.id = :idAntecedenteColegioBuscar")
    void actualizarUsuarioId(@Param("idAntecedenteColegioBuscar") Long idAntecedenteColegioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.AntecedenteColegio} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.AntecedenteColegio}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AntecedenteColegio AS a WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);




    /**
     * Obtiene un {@link crm.entities.AntecedenteColegio} segun su id
     *
     * @param id id del {@link crm.entities.AntecedenteColegio}
     *
     * @return  {@link crm.entities.AntecedenteColegio} buscado
     *
     * @author Fernando Da Silva <fernando.dasilva@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteColegio AS a WHERE a.id = :id ")
    AntecedenteColegio getAntecedenteColegioById(@Param("id") Long id);




    /**
     * Obtiene un listado de  {@link crm.entities.AntecedenteColegio}, según un id de {@link crm.entities.Colegio}
     *
     * @param codColegio id de la {@link crm.entities.Colegio} de las ante{@link crm.entities.AntecedenteColegio}
     *
     * @return  Lista con {@link crm.entities.AntecedenteColegio} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteColegio AS a WHERE a.colegio.codigo = :codColegio ")
    List<AntecedenteColegio> buscarPorCodColegio(@Param("codColegio") Integer codColegio);
}
