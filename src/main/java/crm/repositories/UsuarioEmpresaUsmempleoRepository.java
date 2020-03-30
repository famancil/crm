package crm.repositories;

import crm.entities.UsuarioEmpresaUsmempleo;
import crm.entities.UsuarioUsmempleoEmpresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioEmpresaUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface UsuarioEmpresaUsmempleoRepository extends CrudRepository<UsuarioEmpresaUsmempleo, Short> {


    /**
     * Obtiene un objeto {@link crm.entities.UsuarioEmpresaUsmempleo}, que posea un identificador igual al dado como
     * parametro.
     *
     * @param idUsuarioEmpresaUsmempleo identificador del {@link crm.entities.UsuarioEmpresaUsmempleo} deseado.
     *
     * @return Rero{@link crm.entities.UsuarioEmpresaUsmempleo}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    UsuarioEmpresaUsmempleo findById(Long idUsuarioEmpresaUsmempleo);


    /**
     * Obtiene una lista de nombres completos de {@link crm.entities.UsuarioEmpresaUsmempleo} que tengan nombre igual al parametro entregado
     *
     * @param tagName Nombre del usuario a buscar.
     *
     * @return {@link java.util.List} de {@link java.lang.String}.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT u.nombreCompleto FROM UsuarioEmpresaUsmempleo u WHERE UPPER(translate(u.nombreCompleto ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT(UPPER(translate(:tagName,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')")
    List<String> buscarUsuariosEmpresaPorCalceNombreCompleto(@Param("tagName") String tagName);
}
