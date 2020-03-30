package crm.repositories;

import crm.entities.TipoMoneda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoMoneda}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoMonedaRepository extends CrudRepository<TipoMoneda, Short> {

    /**
     * Retorna una instancia de {@link crm.entities.TipoMoneda} segun un id dado.
     * @param cod
     * @return Objeto de {@link crm.entities.TipoMoneda}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    TipoMoneda findByCodigo(Short cod);




    /**
     * Retorna una lista de {@link crm.entities.TipoMoneda} ordenadas por el nombre
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoMoneda}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoMoneda> findAllByOrderByNombreAsc();

}
