package crm.repositories;

import crm.entities.CompetenciaUsmempleo;
import crm.entities.TipoCompetencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link CompetenciaUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface CompetenciaUsmempleoRepository extends CrudRepository<CompetenciaUsmempleo, Short>{




    /**
     * Obtiene una lista con todas los {@link CompetenciaUsmempleo} registradas en el sistema
     *
     * @return Coleccion ({@link List}) de {@link CompetenciaUsmempleo}
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<CompetenciaUsmempleo> findAll();




    /**
     * Obtiene una lista con todas los {@link CompetenciaUsmempleo} asociadas a una {@link crm.entities.TipoCompetencia}
     * según un id de la {@link crm.entities.TipoCompetencia}
     *
     * @return Coleccion ({@link List}) de {@link CompetenciaUsmempleo}
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c " +
            "FROM CompetenciaUsmempleo AS c " +
            "WHERE c.tipoCompetencia.codigo = :codigo " +
            "ORDER BY c.nombre ASC")
    List<CompetenciaUsmempleo> buscarCompetenciaUsmempleoPorIdTipoCompetencia(@Param("codigo") Integer codigo );

}
