package crm.repositories;

import crm.entities.TipoEstadoPago;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstadoPago}.
 *
 * @author Felipe Mancilla S <hector.calquin@alumnos.usm.cl>
 */
public interface TipoEstadoPagoRepository extends CrudRepository<TipoEstadoPago, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoEstadoPago} con todos los tipos de estados de un pago.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoEstadoPago}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    List<TipoEstadoPago> findAll();

}
