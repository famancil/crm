package crm.repositories;

import crm.entities.TipoDominioCompu;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoDominioCompu}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoDominioCompuRepository extends CrudRepository <TipoDominioCompu, Short> {

    /**
     * Retorna una instancia de {@link crm.entities.TipoDominioCompu} segun un id dado.
     * @param cod
     * @return Objeto de {@link crm.entities.TipoDominioCompu}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    TipoDominioCompu findByCodigo(Short cod);
}
