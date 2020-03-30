package crm.repositories;

import crm.entities.TipoNivelInteres;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TipoNivelInteresRepository extends CrudRepository<TipoNivelInteres, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoNivelInteres} con todos los tipos de nivel de interes.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoNivelInteres}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    List<TipoNivelInteres> findAllByOrderByCodNivelInteresAsc();
}
