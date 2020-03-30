package crm.repositories;

import crm.entities.TipoUsuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoUsuario}
 * @auuthor Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoUsuarioRepository extends CrudRepository<TipoUsuario, Short> {

    /**
     * Retorna una lista con todos los {@link crm.entities.TipoUsuario} que hay en la base de datos.
     *
      * @return Collection ({@link java.util.List}) de {@link crm.entities.TipoUsuario}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    List<TipoUsuario> findAll();

    /**
     * Retorna una instancia de {@link crm.entities.TipoUsuario}
     *
     * @return Objeto de {@link crm.entities.TipoUsuario}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    TipoUsuario findByCodigo(Short id);


    /**
     * Retorna una lista con todas los {@link crm.entities.TipoUsuario} ordenada según su nombre
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoUsuario}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoUsuario> findAllByOrderByNombreAsc();
}
