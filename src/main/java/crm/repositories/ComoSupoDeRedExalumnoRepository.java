package crm.repositories;

import crm.entities.ComoSupoDeRedExalumnos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * repositorio para el manejo CRUD de la entidad {@link crm.entities.ComoSupoDeRedExalumnos}
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface ComoSupoDeRedExalumnoRepository extends CrudRepository <ComoSupoDeRedExalumnos, Short>{

    /**
     * Obtiene una lista de {@link crm.entities.ComoSupoDeRedExalumnos}
     * @return Collection ({@link java.util.List}) de {@link crm.entities.ComoSupoDeRedExalumnos}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    List<ComoSupoDeRedExalumnos> findAll();



    /**
     * Retorna una instancia de {@link crm.entities.ComoSupoDeRedExalumnos}, según el codigo
     *
     * @param codigo
     *
     * @return Instancia de {@link crm.entities.ComoSupoDeRedExalumnos}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    ComoSupoDeRedExalumnos findByCodigo(Short codigo);


    /**
     * Retorna una lista con todas los {@link crm.entities.ComoSupoDeRedExalumnos}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.ComoSupoDeRedExalumnos}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<ComoSupoDeRedExalumnos> findAllByOrderByTituloAsc();
}
