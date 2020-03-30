package crm.repositories;

import crm.entities.TipoCampana;
import crm.entities.TipoContacto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoCampana}, tabla dbo.tipo_campana
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoCampanaRepository extends CrudRepository<TipoCampana, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoCampana}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoCampana}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoCampana> findAll();

    /**
     * Retorna una lista con todas los {@link crm.entities.TipoCampana}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoCampana}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoCampana> findAllByOrderByNombreAsc();
}
