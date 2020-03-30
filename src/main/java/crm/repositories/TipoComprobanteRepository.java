package crm.repositories;

import crm.entities.TipoComprobante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by famancil on 25-11-16.
 */
public interface TipoComprobanteRepository extends CrudRepository<TipoComprobante, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoComprobante} con todos los tipos de comprobante de un aporte.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoComprobante}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    List<TipoComprobante> findAll();
}
