package crm.repositories;

import crm.entities.TipoSituacionLaboral;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSituacionLaboral}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoSituacionLaboralRepository extends CrudRepository <TipoSituacionLaboral, Short> {

    /**
     * Retorna una instancia de {@link crm.entities.TipoSituacionLaboral} segun un id dado.
     * @param cod
     * @return Objeto de {@link crm.entities.TipoSituacionLaboral}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    TipoSituacionLaboral findByCodigo(Short cod);
}
