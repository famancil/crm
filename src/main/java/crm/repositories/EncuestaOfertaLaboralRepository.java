package crm.repositories;

import crm.entities.EncuestaOfertaLaboral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo de CRUD de {@link crm.entities.EncuestaOfertaLaboral}
 *
 * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
 */
public interface EncuestaOfertaLaboralRepository extends CrudRepository<EncuestaOfertaLaboral, Long> {

    /**
     * Retorna una lista de {@link crm.entities.EncuestaOfertaLaboral} que fueron respondidas por una empresa
     * en particular
     *
     * @param idEmpresa El id de la empresa a buscar
     * @return Lista de {@link crm.entities.EncuestaOfertaLaboral}
     */
    @Query("SELECT e FROM EncuestaOfertaLaboral AS e WHERE e.idPerfilEmpresa = :idEmpresa")
    public List<EncuestaOfertaLaboral> buscarPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna la cantidad de encuestas de ofertas laborales respondidas
     *
     * @return {@link java.lang.Long}
     */
    @Query("SELECT COUNT(e) FROM EncuestaOfertaLaboral AS e WHERE e.opina = TRUE")
    public Long contarEncuestasRespondidas();
}
