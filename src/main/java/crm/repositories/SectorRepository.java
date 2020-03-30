package crm.repositories;

import crm.entities.TipoSector;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoSector}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface SectorRepository extends CrudRepository<TipoSector, Short> {
    /**
     * Retorna el listado de todos los {@link crm.entities.TipoSector} que se encuentren en la base de datos
     * ordenados por codigo de forma ascendente.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoSector}.
     */
    List<TipoSector> findAllByOrderByCodigoAsc();

    /**
     * Retorna el listado de todos los {@link crm.entities.TipoSector} que se encuentren en la base de datos.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.TipoSector}.
     */
    List<TipoSector> findAll();

    /**
     * Retorna el nombre de un {@link crm.entities.TipoSector} con un codSector especifico.
     *
     * @return String Nombre de un {@link crm.entities.TipoSector}.
     */
    @Query("SELECT ts.nombre " +
            "FROM TipoSector AS ts " +
            "WHERE ts.codigo = :codigoSector")
    String buscarNombreTipoSector(@Param("codigoSector") Short codigoSector);
}