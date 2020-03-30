package crm.repositories;

import crm.entities.FormatoCvUsmempleo;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.FormatoCvUsmempleo}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface FormatoCvUsmempleoRepository extends CrudRepository<FormatoCvUsmempleo, Long> {

    /**
     * Retorna un {@link crm.entities.FormatoCvUsmempleo} que posean un usuarioId
     * igual al parametro que se le entregue
     * @param id id usado para la busqueda.
     * @return {@link crm.entities.FormatoCvUsmempleo}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    FormatoCvUsmempleo findById(Long id);
}
