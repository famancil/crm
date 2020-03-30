package crm.repositories;

import crm.entities.CompromisoEmpresa;
import crm.entities.AporteEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.CompromisoEmpresa}.
 *
 * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
 */
public interface CompromisoEmpresaRepository extends CrudRepository<CompromisoEmpresa, Long> {

    /**
     * Retorna un listado de {@link crm.entities.CompromisoEmpresa} de una sucursal.
     *
     * @param empresa empresa que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.CompromisoEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT ce " +
            "FROM CompromisoEmpresa AS ce " +
            "WHERE ce.sucursalEmpresa.empresa.id = :empresa",
            countQuery = "SELECT COUNT(ce)" +
                    "FROM CompromisoEmpresa ce " +
                    "WHERE ce.sucursalEmpresa.empresa.id = :empresa" )
    List<CompromisoEmpresa> buscarCompromisoEmpresaPorIdEmpresa(@Param("empresa") Long empresa);

    @Query("SELECT SUM(ae.montoPagado) FROM AporteEmpresa AS ae WHERE ae.sucursalEmpresa.sucursalCodigo = :sucursal AND ae.compromisoEmpresa.id = :compromiso ")
    CompromisoEmpresa buscarCompromisoPorIdiso(@Param("sucursal") Long sucursal, @Param("compromiso") Long compromiso);

     /**
     * Retorna el {@link crm.entities.CompromisoEmpresa} que posea un id igual al parametro otorgado
     *
     * @param idCompromsiso del compromiso de la empresa que se desea buscar.
     * @return {@link crm.entities.CompromisoEmpresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */

    CompromisoEmpresa findById(Long idCompromsiso);

    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} que posea un string igual al parametro otorgado.
     *
     * @param compromiso String del tipo compromiso que se desea buscar.
     * @return Collection {@link Page} de {@link crm.entities.CompromisoEmpresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */
    @Query(value="SELECT ce FROM CompromisoEmpresa AS ce " +
            "JOIN ce.tipoCompromiso AS tc " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%')",
            countQuery = "SELECT COUNT(ce) FROM CompromisoEmpresa AS ce " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%')")
    Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromiso(@Param("nomCompromiso") String compromiso,Pageable page);

    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} que posea un string igual al parametro otorgado.
     *
     * @param compromiso String del tipo compromiso que se desea buscar.
     * @return Collection {@link Page} de {@link crm.entities.CompromisoEmpresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */

    @Query(value="SELECT ce FROM CompromisoEmpresa AS ce " +
            "JOIN ce.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
            "AND ts.codigo = :codSector",
            countQuery = "SELECT COUNT(ce) FROM CompromisoEmpresa AS ce " +
                    "JOIN ce.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%')" +
                    "AND ts.codigo = :codSector")
    Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoYTipoSector(@Param("nomCompromiso") String compromiso,Pageable page,@Param("codSector") Short sector);

    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} con un tipo de compromiso y de vigencia otorgadas por parametros
     *
     * @param compromiso String del tipo compromiso que se desea buscar.
     * @param vigencia String del tipo vigencia que se desea buscar.
     * @param sector Short del sector de una empresa comprometida.
     *
     * @return Collection {@link Page} de {@link crm.entities.CompromisoEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */
    @Query(value="SELECT ce FROM CompromisoEmpresa AS ce " +
            "JOIN ce.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia " +
            "AND ts.codigo = :codSector",
            countQuery = "SELECT COUNT(ce) FROM CompromisoEmpresa AS ce " +
                    "JOIN ce.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia " +
                    "AND ts.codigo = :codSector")
    Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYTipoSector(@Param("nomCompromiso") String compromiso,@Param("vigencia") String vigencia,Pageable page,@Param("codSector") Short sector);

    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} con un tipo de compromiso y de vigencia otorgadas por parametros
     *
     * @param compromiso String del tipo compromiso que se desea buscar.
     * @param vigencia String del tipo vigencia que se desea buscar.
     *
     * @return Collection {@link Page} de {@link crm.entities.CompromisoEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */
    @Query(value="SELECT ce FROM CompromisoEmpresa AS ce " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia",
            countQuery = "SELECT COUNT(ce) FROM CompromisoEmpresa AS ce " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia")
    Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoYTipoVigencia(@Param("nomCompromiso") String compromiso,@Param("vigencia") String vigencia,Pageable page);


    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} con un tipo de compromiso y de vigencia, ademas de si hicieron aportes.
     *
     * @param compromiso String del tipo compromiso que se desea buscar.
     * @param vigencia String del tipo vigencia que se desea buscar.
     *
     * @return Collection {@link Page} de {@link crm.entities.CompromisoEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */
    @Query(value="SELECT ce FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia",
            countQuery = "SELECT COUNT(ce) FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia = :vigencia")
    Page<CompromisoEmpresa> buscarCompromisoEmpresaPorTipoCompromisoTipoVigenciaYAporte(@Param("nomCompromiso") String compromiso,@Param("vigencia") String vigencia,Pageable page);

    /**
     * Retorna un Page de {@link crm.entities.CompromisoEmpresa} con la id del usuario pasado por parametro.
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado.
     *
     * @return Collection {@link List} de {@link crm.entities.CompromisoEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */
    @Query("SELECT ce FROM CompromisoEmpresa AS ce " +
            "JOIN ce.operadorACargo AS us " +
            "WHERE us.id = :idUsuario")
    List<CompromisoEmpresa> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);

    /**
     * Elimina un {@link crm.entities.CompromisoEmpresa} segun el id especificado como parametro
     *
     * @param idCompromisoBuscar Id del {@link crm.entities.OperadorContabilidad}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM CompromisoEmpresa AS ce WHERE ce.id = :idCompromisoBuscar")
    void eliminar(@Param("idCompromisoBuscar") Long idCompromisoBuscar);




}
