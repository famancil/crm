package crm.repositories;

import crm.entities.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link AdminOfertaLaboralUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface AdminOfertaLaboralUsmempleoRepository extends CrudRepository<AdminOfertaLaboralUsmempleo, AdminOfertaLaboralUsmempleoPK> {

    /**
     * Obtiene una lista con todas las {@link AdminOfertaLaboralUsmempleo} registradas en el sistema
     *
     * @return Coleccion ({@link List}) de {@link AdminOfertaLaboralUsmempleo}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<AdminOfertaLaboralUsmempleo> findAll();

    /**
     * Cuenta la cantidad de tipos de administradores en ofertas laborales
     *
     * @return String de la forma "creadores, publicadores"
     */
    @Query("SELECT COUNT(CASE WHEN a.creador = TRUE THEN 1 END), " +
            "COUNT(CASE WHEN a.publicador = TRUE THEN 1 END) " +
            "FROM AdminOfertaLaboralUsmempleo a")
    String contarPorTipo();

}
