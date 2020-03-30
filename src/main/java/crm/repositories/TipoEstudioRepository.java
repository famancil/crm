package crm.repositories;

import crm.entities.TipoEstudio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.TipoEstudio}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface TipoEstudioRepository extends CrudRepository<TipoEstudio, Short>{


    /**
     * Retorna una List con {@link crm.entities.AntecedenteEducacional} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @return List con {@link crm.entities.AntecedenteEducacional} asociados a un {@link crm.entities.Usuario}
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT t FROM TipoEstudio AS t WHERE cod_estudio < 7 OR cod_estudio > 9 ")
    List<TipoEstudio> getEstudiosUniversitarios();




    /**
     * Retorna una List con {@link crm.entities.AntecedenteEducacional} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @return List con {@link crm.entities.AntecedenteEducacional} asociados a un {@link crm.entities.Usuario}
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT t FROM TipoEstudio AS t WHERE cod_estudio > 6 AND cod_estudio < 10 ")
    List<TipoEstudio> getEstudiosEscolares();



    /**
     * Obtiene un listado de todos los {@link crm.entities.TipoEstudio} correspondientes a los utilizados en los
     *  {@link crm.entities.FiltroOfertaLaboral} (Pregrado, Doctorado, Postdoctorado, Magister), ordenadas por nombre
     *
     * @return Lista de todos los {@link crm.entities.TipoEstudio} ordenadas por nombre
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT t " +
            "FROM TipoEstudio AS t " +
            "WHERE t.codEstudio <= 4 ")
    List<TipoEstudio> listaTipoEstudioDeFiltroOfertaLaboralOrdenPorNombreAsc();



    /**
     * Obtiene una {@link crm.entities.TipoEstudio} según id de busqqueda
     *
     * @param id Id de la {@link crm.entities.TipoEstudio} a obtener.
     *
     * @return Retorna {@link crm.entities.TipoEstudio} buscada
     *
     * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    TipoEstudio findByCodEstudio(Short id);

}
