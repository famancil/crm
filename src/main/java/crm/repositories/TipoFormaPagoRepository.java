package crm.repositories;

import crm.entities.TipoFormaPago;
import crm.entities.TipoOportunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoFormaPago}.
 *
 * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
 */
public interface TipoFormaPagoRepository extends CrudRepository<TipoFormaPago, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoFormaPago} con todas los tipos de forma de pago de la tabla crm.tipo_forma_pago
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoFormaPago}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT tf FROM TipoFormaPago AS tf ORDER BY tf.id ")
    List<TipoFormaPago> findAll();
}
