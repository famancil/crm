package crm.repositories;

import crm.entities.EstadoSolicitudCredencial;
import crm.entities.TipoCargo;
import crm.entities.TipoOferta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link TipoOferta}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoOfertaRepository extends CrudRepository<TipoOferta, Short>{


    /**
     * Retorna el listado de todos los {@link crm.entities.TipoOferta} que se encuentren en la base de datos.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoOferta}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoOferta> findAll();
}
