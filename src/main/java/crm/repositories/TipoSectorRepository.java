package crm.repositories;

import crm.entities.TipoSector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSector}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface TipoSectorRepository extends CrudRepository<TipoSector, Short> {

    /**
     * Retorna un objeto de {@link crm.entities.TipoSector} según un id dado
     *
     * @param codigoSector Id del TipoSector a buscar.
     *
     * @return Instancia de {@link crm.entities.TipoSector}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT ts.nombre " +
            "FROM TipoSector AS ts " +
            "WHERE ts.codigo = :codigoSector")
    String buscarNombreTipoSector(@Param("codigoSector") Short codigoSector);
}
