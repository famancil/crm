package crm.repositories;

import crm.entities.Asesor;
import crm.entities.GrupoEmpleo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.GrupoEmpleo}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface GrupoEmpleoRepository extends CrudRepository<GrupoEmpleo, Short> {






    /**
     * Obtiene un listado de  {@link crm.entities.GrupoEmpleo}, según un id de {@link crm.entities.Institucion}
     *
     * @param codInstitucion id de la {@link crm.entities.Institucion} de las {@link crm.entities.GrupoEmpleo}
     *
     * @return  Lista con {@link crm.entities.GrupoEmpleo} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT g FROM GrupoEmpleo AS g WHERE g.institucion.codInstitucion = :codInstitucion ")
    List<GrupoEmpleo> buscarPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);




}
