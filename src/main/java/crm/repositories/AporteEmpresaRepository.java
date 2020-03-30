package crm.repositories;

import crm.entities.AporteEmpresa;
import crm.entities.CompromisoEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AporteEmpresa}.
 *
 * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
 */
public interface AporteEmpresaRepository extends CrudRepository<AporteEmpresa, Long> {

    /**
     * Retorna un listado de {@link crm.entities.AporteEmpresa} de una sucursal.
     *
     * @param idEmpresa id de la empresa que se desea buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.AporteEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT ae FROM AporteEmpresa AS ae WHERE ae.sucursalEmpresa.empresa.id = :idEmpresa ")
    List<AporteEmpresa> buscarAporteEmpresaPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna el {@link crm.entities.AporteEmpresa} que posea un id igual al parametro otorgado
     *
     * @param idAporte del aporte de la empresa que se desea buscar.
     * @return {@link crm.entities.AporteEmpresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>     */

    AporteEmpresa findById(Long idAporte);

    /**
     * Retorna el total de {@link crm.entities.AporteEmpresa} de una empresa entregada por parametro.
     *
     * @param idEmpresa id de la empresa que se desea buscar.
     *
     * @return Integer de {@link crm.entities.AporteEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT SUM(ae.montoPagado) FROM AporteEmpresa AS ae WHERE ae.sucursalEmpresa.empresa.id = :idEmpresa " +
            "AND ae.compromisoEmpresa.id = :idCompromiso")
    Integer obtenertotalAporteEmpresaPorEmpresaYCompromiso(@Param("idEmpresa") Long idEmpresa, @Param("idCompromiso") Long idCompromiso);

    /**
     * Retorna el total de {@link crm.entities.AporteEmpresa} de una sucursal y un compromiso entregado por parametros.
     *
     * @param sucursal codigo de la sucursal de la empresa que se desea buscar.
     * @param compromiso ide del compromiso de la sucursal que se desea buscar.
     *
     * @return Integer de {@link crm.entities.AporteEmpresa}.
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT SUM(ae.montoPagado) FROM AporteEmpresa AS ae WHERE ae.sucursalEmpresa.sucursalCodigo = :sucursal AND ae.compromisoEmpresa.id = :compromiso ")
    Integer totalAporteEmpresaPorSucursalYCompromiso(@Param("sucursal") Long sucursal, @Param("compromiso") Long compromiso);

    /**
     * Elimina un {@link crm.entities.AporteEmpresa} segun la id del compromiso especificada como parametro.
     *
     * @param idCompromisoEmpresaBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.AporteEmpresa}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AporteEmpresa AS ae WHERE ae.compromisoEmpresa.id = :idCompromisoEmpresaBuscar")
    void eliminar(@Param("idCompromisoEmpresaBuscar") Long idCompromisoEmpresaBuscar);

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa} de un tipo de compromiso.
     *
     * @param nombreCompromiso String del tipo de compromiso de la empresa que se desea buscar.
     * @param sector Short del sector al que pertenece la empresa.
     *
     * @return Coleccion {@Link java.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT ae FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
            "AND ts.codigo = :codSector",
            countQuery = "SELECT COUNT(ae) FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
                    "AND ts.codigo = :codSector")
    Page <AporteEmpresa> buscarAporteEmpresaPorTipoCompromisoYTipoSector(@Param("nomCompromiso") String nombreCompromiso, Pageable page, @Param("codSector") Short sector);

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa} de un tipo de compromiso, tipo de sector y tipo de vigencia especificos.
     *
     * @param nombreCompromiso String del tipo de compromiso de la empresa que se desea buscar.
     * @param sector Short del sector al que pertenece la empresa.
     * @param vigencia String de la vigencia del compromiso.
     *
     * @return Coleccion {@Link java.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT ae FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
            "AND ts.codigo = :codSector AND tv.nomVigencia = :vigencia",
            countQuery = "SELECT COUNT(ae) FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
                    "AND ts.codigo = :codSector AND tv.nomVigencia = :vigencia")
    Page <AporteEmpresa> buscarAporteEmpresaPorTipoCompromisoYTipoVigenteYTipoSector(@Param("nomCompromiso") String nombreCompromiso,@Param("vigencia") String vigencia, Pageable page, @Param("codSector") Short sector);


    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa} de un tipo de compromiso y vigencia especificadas.
     *
     * @param nombreCompromiso String del tipo de compromiso de la empresa que se desea buscar.
     * @param vigencia String de la vigencia del compromiso.
     *
     * @return Coleccion {@Link java.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT ae FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
            "AND tv.nomVigencia = :vigencia",
            countQuery = "SELECT COUNT(ae) FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') " +
                    "AND tv.nomVigencia = :vigencia")
    Page <AporteEmpresa> buscarAporteEmpresaPorTipoCompromisoYTipoVigente(@Param("nomCompromiso") String nombreCompromiso,@Param("vigencia") String vigencia, Pageable page);

    /**
     * Retorna  una {@Link java.util.Page} con todas los {@Link crm.entities.AporteEmpresa} de un tipo de compromiso.
     *
     * @param nombreCompromiso String del tipo de compromiso de la empresa que se desea buscar.
     *
     * @return Coleccion {@Link java.util.Page} de los {@Link crm.entities.AporteEmpresa} de una {@Link crm.entities.Empresa}
     *
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT ae FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN se.empresa AS pe " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%')",
            countQuery = "SELECT COUNT(ae) FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN se.empresa AS pe " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%')")
    Page <AporteEmpresa> buscarAporteEmpresaPorTipoCompromiso(@Param("nomCompromiso") String nombreCompromiso, Pageable page);


    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.AporteEmpresa} por año
     * de todas los {@link crm.entities.Empresa}, realizando una busqueda según un año, tipo de oportunidad y vigencia como parametros.
     *
     * @param compromiso String del tipo de oportunidad del compromiso de la empresa que se desea buscar.
     * @param vigencia String del tipo de vigencia del compromiso de la empresa que se desea buscar.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.AporteEmpresa} por año
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT  pe," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END) ) AS long) AS total , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) " +
            "FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "JOIN se.empresa AS pe " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia LIKE CONCAT(:vigencia,'%') " +
            "GROUP BY  pe ORDER BY total DESC",
            countQuery = "SELECT COUNT(pe)," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) " +
                    "FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "JOIN se.empresa AS pe " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia LIKE CONCAT(:vigencia,'%') " +
                    "GROUP BY  pe")
    ArrayList<ArrayList<String>> indiceCantidadAportesEmpresas(@Param("nomCompromiso") String compromiso,@Param("vigencia") String vigencia,Pageable page);

    @Query(value="SELECT  pe," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END) ) AS long) AS total , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
            "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END + " +
            "CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) " +
            "FROM AporteEmpresa AS ae " +
            "JOIN ae.compromisoEmpresa AS ce " +
            "JOIN ae.sucursalEmpresa AS se " +
            "JOIN ce.tipoCompromiso AS tc " +
            "JOIN ce.tipoVigencia AS tv " +
            "JOIN se.empresa AS pe " +
            "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia LIKE CONCAT(:vigencia,'%') " +
            "GROUP BY  pe ORDER BY total DESC",
            countQuery = "SELECT COUNT(pe)," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END) ) AS long) , " +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) ," +
                    "CAST ( ROUND(SUM(CASE WHEN YEAR(ae.fecha)=2016 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2015 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2014 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2013 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2012 THEN ae.montoPagado ELSE 0 END + " +
                    "CASE WHEN YEAR(ae.fecha)=2011 THEN ae.montoPagado ELSE 0 END) ) AS long) " +
                    "FROM AporteEmpresa AS ae " +
                    "JOIN ae.compromisoEmpresa AS ce " +
                    "JOIN ae.sucursalEmpresa AS se " +
                    "JOIN ce.tipoCompromiso AS tc " +
                    "JOIN ce.tipoVigencia AS tv " +
                    "JOIN se.empresa AS pe " +
                    "WHERE tc.nomCompromiso LIKE CONCAT(:nomCompromiso ,'%') AND tv.nomVigencia LIKE CONCAT(:vigencia,'%') " +
                    "GROUP BY  pe")
    ArrayList<ArrayList<String>> indiceCantidadAportesEmpresasGraf(@Param("nomCompromiso") String compromiso,@Param("vigencia") String vigencia);

}
