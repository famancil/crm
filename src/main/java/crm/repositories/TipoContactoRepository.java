package crm.repositories;

import crm.entities.TipoContacto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoContacto}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoContactoRepository extends CrudRepository<TipoContacto, Short> {

    /**
     * Retorna una lista de {@link crm.entities.TipoContacto} con todas los tipos de contacto de la tabla dbo.tipo_contacto
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoContacto}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<TipoContacto> findAll();



    /**
     * Retorna un objeto de {@link crm.entities.TipoContacto} según un id dado
     *
     * @param codContacto Identificador del TipoContacto a buscar.
     *
     * @return Instancia de {@link crm.entities.TipoContacto}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoContacto findByCodContacto(Short codContacto);
}
