package crm.repositories;

import crm.entities.Pais;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Pais}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface PaisRepository extends CrudRepository<Pais, Short>{

    /**
     * Retorna una lista con todos los paises
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Pais}.
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    List<Pais> findAllByOrderByNombreAsc();

    /**
     * Retorna una instancia de {@link crm.entities.Pais} segun un id dado.
     * @param id
     * @return Objeto de {@link crm.entities.Pais}
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    Pais findById(Short id);

    /**
     * Retorna una lista con todos los paises que estan asociados a alguna empresa
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Pais}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT p FROM Empresa AS e JOIN e.pais AS p WHERE e.pais.codigo = p.codigo")
    List<Pais> cargarPaisesEmpresas();
}
