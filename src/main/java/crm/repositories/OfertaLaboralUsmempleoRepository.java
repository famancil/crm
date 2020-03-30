package crm.repositories;

import crm.entities.CompetenciaUsmempleo;
import crm.entities.OfertaLaboralUsmempleo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface OfertaLaboralUsmempleoRepository extends CrudRepository<OfertaLaboralUsmempleo, Long> {
    /**
     * Devuelve una lista de {@link java.lang.Integer}
     * de los años existentes de las fechas de publicaciones de ofertas de una empresa
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado de años.
     * @return Colleccion {@Link java.util.List} de  {@link java.lang.Integer} que corresponden
     * a años de las publicaciones de ofertas laborales que ha hecho la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select DISTINCT extract(YEAR from ofelabusm_fecha_publicacion) from empleo.oferta_laboral_usmempleo where perempusm_id = :idEmpresa ORDER BY extract(YEAR from ofelabusm_fecha_publicacion) DESC", nativeQuery = true)
    List<Double> getListaAniosOfertasEmpresas(@Param("idEmpresa") Long idEmpresa);

    /**
     * Devuelve el total de {@link crm.entities.OfertaLaboralUsmempleo}
     * que ha publicado la empresa con id idEmpresa
     *
     * @param idEmpresa id de empresa a la cual se le quiere calcular el total de ofertas laborales
     * @return Numero {@link java.lang.Integer} total de ofertas laborales de la empresa con id idEmpresa
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select COUNT(o) from empleo.oferta_laboral_usmempleo o where perempusm_id = :idEmpresa", nativeQuery = true)
    Integer getSumaTotalOfertasEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Devuelve el total de {@link crm.entities.OfertaLaboralUsmempleo}
     * que ha publicado la empresa con id idEmpresa en el mes mes
     *
     * @param idEmpresa id de empresa a la cual se le quiere calcular el total de ofertas laborales
     * @param mes mes al cual se le quiere calcular el total de ofertas
     * @return Numero {@link java.lang.Integer} total de ofertas laborales de la empresa con id idEmpresa en el mes
     * mes
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select COUNT(o) from empleo.oferta_laboral_usmempleo o where perempusm_id = :idEmpresa AND extract(MONTH from ofelabusm_fecha_publicacion) = :mes", nativeQuery = true)
    Integer getSumaOfertasMesEmpresa(@Param("idEmpresa") Long idEmpresa,@Param("mes") Integer mes);

    /**
     * Devuelve el total de {@link crm.entities.OfertaLaboralUsmempleo}
     * que ha publicado la empresa con id idEmpresa en el anio anio
     *
     * @param idEmpresa id de empresa a la cual se le quiere calcular el total de ofertas laborales
     * @param anio anio al cual se le quiere calcular el total de ofertas
     * @return Numero {@link java.lang.Integer} total de ofertas laborales de la empresa con id idEmpresa en el anio
     * anio
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select COUNT(o) from empleo.oferta_laboral_usmempleo o where perempusm_id = :idEmpresa AND extract(YEAR from ofelabusm_fecha_publicacion) = :anio", nativeQuery = true)
    Integer getSumaOfertasAnioEmpresa(@Param("idEmpresa") Long idEmpresa,@Param("anio") Double anio);

    /**
     * Devuelve el total de {@link crm.entities.OfertaLaboralUsmempleo}
     * que ha publicado la empresa con id idEmpresa en el mes mes del anio anio
     *
     * @param idEmpresa id de empresa a la cual se le quiere calcular el total de ofertas laborales
     * @param anio anio al cual se le quiere calcular el total de ofertas
     * @param mes mes del anio al cual se le requiere calcular el total de ofertas
     * @return Numero {@link java.lang.Integer} total de ofertas laborales de la empresa con id idEmpresa en el mes mes del anio
     * anio
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select COUNT(o) from empleo.oferta_laboral_usmempleo o where perempusm_id = :idEmpresa AND extract(YEAR from ofelabusm_fecha_publicacion) = :anio AND extract(MONTH from ofelabusm_fecha_publicacion) = :mes", nativeQuery = true)
    Integer getSumaOfertasMesAnioEmpresa(@Param("idEmpresa") Long idEmpresa,@Param("anio") Double anio,@Param("mes") Integer mes);

    /**
     * Devuelve las {@link crm.entities.OfertaLaboralUsmempleo}
     * que ha publicado la empresa con id idEmpresa
     *
     * @param idEmpresa id de empresa a la cual se le quiere calcular el total de ofertas laborales
     * @return Coleccion {@Link java.util.List} de {@Link crm.entities.OfertaLaboralUsmempleo} de empresas con idEmpresa
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("select o from OfertaLaboralUsmempleo o where o.empresa.id = :idEmpresa order by o.fechaPublicacion ASC")
    List<OfertaLaboralUsmempleo> getOfertasLaboralesEmpresaDetalle(@Param("idEmpresa") Long idEmpresa);

    @Query(value="select o from OfertaLaboralUsmempleo o where o.empresa.id = :idEmpresa order by o.fechaPublicacion ASC",
            countQuery = "select count(o) from OfertaLaboralUsmempleo o where o.empresa.id = :idEmpresa")
    Page<OfertaLaboralUsmempleo> getOfertasLaboralesEmpresaDetallePrimeras20(@Param("idEmpresa") Long idEmpresa, Pageable page);




    /**
     * Obtiene un listado por mes de la cantidad de {@link crm.entities.Empresa} por que ofrecen {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según un año y tipoVigencia especificados como parametros. Solamente son obtenidos los
     * meses que cuenten ofertas de empresas con los parametros de busqueda especificados.
     *
     * @param codVigencia Codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de los Meses; para cada una de ellos
     *          coleccion {@Link java.util.ArrayList}, contenedora del numero del mes y la cantidad de {@link crm.entities.Empresa}
     *          asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT MONTH(o.fechaPublicacion), " +
            "COUNT( DISTINCT e ) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "JOIN o.empresa AS e " +
            "WHERE TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY MONTH(o.fechaPublicacion) " +
            "ORDER BY MONTH(o.fechaPublicacion) ")
    ArrayList<ArrayList<String>> indiceCantidadEmpresasOfrecenEmpleo( @Param("codVigencia") String codVigencia,
                                                                      @Param("anio") Integer anio);



    /**
     * Obtiene un listadode la cantidad de {@link crm.entities.OfertaLaboralUsmempleo} según el
     * {@link crm.entities.TipoOferta}, realizando una busqueda según un año y tipoVigencia especificados como
     * parametros.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.TipoOferta}, que contiene una
     *          coleccción {@Link java.util.ArrayList} que almacena el nombre del {@link crm.entities.TipoOferta} y la
     *          cantidad de {@link crm.entities.OfertaLaboralUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT to.nombre, " +
            "COUNT( o ) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.oferta AS to " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
                "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY to.nombre " +
            "ORDER BY to.nombre")
    ArrayList<ArrayList<String>> indiceCantidadOfertaLaboralPorTipoOferta( @Param("codVigencia") String codVigencia,
                                                                           @Param("anio") Integer anio);






    /**
     *
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value = "SELECT   COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 1 THEN 1 END) AS ene, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 2 THEN 1 END) AS feb, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 3 THEN 1 END) AS mar, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 4 THEN 1 END) AS abr, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 5 THEN 1 END) AS may, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 6 THEN 1 END) AS jun, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 7 THEN 1 END) AS jul, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 8 THEN 1 END) AS ago, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 9 THEN 1 END) AS sep, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 10 THEN 1 END) AS oct, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 11 THEN 1 END) AS nov, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 12 THEN 1 END) AS dic, " +
                            "COUNT( DISTINCT o ) AS total " +
                    "FROM empleo.oferta_carrera_usmempleo AS oc  " +
                    "JOIN empleo.oferta_laboral_usmempleo AS o " +
                    "ON oc.ofelabusm_id = o.ofelabusm_id  " +
                    "RIGHT JOIN empleo.tipo_oferta AS tipo_oferta  " +
                    "ON o.cod_oferta = tipo_oferta.cod_oferta  " +
                        "AND EXTRACT(YEAR FROM o.ofelabusm_fecha_publicacion) = :anio " +
                        "AND oc.cod_carrera = :codCarrera " +
                    "WHERE tipo_oferta.cod_oferta = :codigoTipoOferta " +
                    "GROUP BY tipo_oferta.nom_oferta " +
                    "ORDER BY tipo_oferta.nom_oferta"
            , nativeQuery = true)
    ArrayList<ArrayList<String>> indiceCantidadOfertaLaboralPorTipoOfertaSegunCarreraYAnio(@Param("codCarrera") Long codCarrera, @Param("anio") Short anio, @Param("codigoTipoOferta") Short codigoTipoOferta);






    /**
     *
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // native query ya que esca
    @Query(value = "SELECT   SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 1 THEN o.ofelabusm_vacantes ELSE 0 END) AS ene, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 2 THEN o.ofelabusm_vacantes ELSE 0 END) AS feb, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 3 THEN o.ofelabusm_vacantes ELSE 0 END) AS mar, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 4 THEN o.ofelabusm_vacantes ELSE 0 END) AS abr, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 5 THEN o.ofelabusm_vacantes ELSE 0 END) AS may, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 6 THEN o.ofelabusm_vacantes ELSE 0 END) AS jun, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 7 THEN o.ofelabusm_vacantes ELSE 0 END) AS jul, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 8 THEN o.ofelabusm_vacantes ELSE 0 END) AS ago, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 9 THEN o.ofelabusm_vacantes ELSE 0 END) AS sep, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 10 THEN o.ofelabusm_vacantes ELSE 0 END) AS oct, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 11 THEN o.ofelabusm_vacantes ELSE 0 END) AS nov, " +
                            "SUM ( CASE WHEN EXTRACT(MONTH FROM o.ofelabusm_fecha_publicacion)= 12 THEN o.ofelabusm_vacantes ELSE 0 END) AS dic, " +
                            "SUM ( CASE WHEN o.ofelabusm_vacantes IS NOT NULL THEN o.ofelabusm_vacantes ELSE 0 END ) AS total " +
                    "FROM empleo.oferta_laboral_usmempleo AS o " +
                    "RIGHT JOIN empleo.tipo_oferta AS tipo_oferta " +
                    "ON o.cod_oferta = tipo_oferta.cod_oferta " +
                    "AND o.ofelabusm_id IN (" +
                                "SELECT DISTINCT oferta.ofelabusm_id " +
                                "FROM empleo.oferta_carrera_usmempleo AS ofertac " +
                                "JOIN empleo.oferta_laboral_usmempleo AS oferta " +
                                "ON ofertac.ofelabusm_id = oferta.ofelabusm_id " +
                                "WHERE EXTRACT(YEAR FROM oferta.ofelabusm_fecha_publicacion) = :anio  " +
                                "AND ofertac.cod_carrera = :codCarrera ) " +
                    "WHERE tipo_oferta.cod_oferta = :codigoTipoOferta " +
                    "GROUP BY tipo_oferta.nom_oferta " +
                    "ORDER BY tipo_oferta.nom_oferta ",
            nativeQuery = true)
    ArrayList<ArrayList<String>> indiceCantidadVacantesOfertaLaboralPorTipoOfertaSegunCarreraYAnio(@Param("codCarrera") Long codCarrera, @Param("anio") Short anio, @Param("codigoTipoOferta") Short codigoTipoOferta);






    /**
     *
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value = "SELECT   COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 1 THEN 1 END) AS ene, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 2 THEN 1 END) AS feb, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 3 THEN 1 END) AS mar, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 4 THEN 1 END) AS abr, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 5 THEN 1 END) AS may, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 6 THEN 1 END) AS jun, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 7 THEN 1 END) AS jul, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 8 THEN 1 END) AS ago, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 9 THEN 1 END) AS sep, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 10 THEN 1 END) AS oct, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 11 THEN 1 END) AS nov, " +
                            "COUNT ( CASE WHEN EXTRACT(MONTH FROM p.posofelabusm_fecha_postulacion)= 12 THEN 1 END) AS dic, " +
                            "COUNT ( DISTINCT p ) AS total " +
                        "FROM empleo.oferta_carrera_usmempleo AS oc " +
                        "JOIN empleo.oferta_laboral_usmempleo AS o " +
                        "ON oc.ofelabusm_id = o.ofelabusm_id  " +
                            "AND oc.cod_carrera = :codCarrera " +
                        "JOIN empleo.postulacion_ofe_lab_usmempleo AS p " +
                        "ON o.ofelabusm_id = p.ofelabusm_id " +
                            "AND EXTRACT(YEAR FROM p.posofelabusm_fecha_postulacion) = :anio " +
                        "RIGHT JOIN empleo.tipo_oferta AS tipo_oferta " +
                        "ON o.cod_oferta = tipo_oferta.cod_oferta " +
                        "WHERE tipo_oferta.cod_oferta = :codigoTipoOferta " +
                        "GROUP BY tipo_oferta.nom_oferta " +
                        "ORDER BY tipo_oferta.nom_oferta" ,
            nativeQuery = true)
    ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertaLaboralPorTipoOfertaSegunCarreraYAnio(@Param("codCarrera") Long codCarrera, @Param("anio") Short anio, @Param("codigoTipoOferta") Short codigoTipoOferta);








    /**
     * Obtiene un listado por segmento, de la cantidad de vacantes de la {@link crm.entities.OfertaLaboralUsmempleo},
     * según su vigencia y año de publicación  .
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * Coleccion {@Link java.util.List} por segmento de vacantes, de la cantidad de vacantes de las
     * {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o.vacantes, " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=1 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=2 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=3 THEN o.vacantes ELSE 0 END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=4 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=5 THEN o.vacantes ELSE 0 END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=6 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=7 THEN o.vacantes ELSE 0 END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=8 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=9 THEN o.vacantes ELSE 0 END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=10 THEN o.vacantes ELSE 0 END) ," +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=11 THEN o.vacantes ELSE 0 END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=12 THEN o.vacantes ELSE 0 END) ," +
            "SUM(o.vacantes) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "WHERE TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY o.vacantes " +
            "ORDER BY o.vacantes")
    ArrayList<ArrayList<String>> indiceSegmentacionVacantes( @Param("codVigencia") String codVigencia,
                                                             @Param("anio") Integer anio);



    /**
     * Obtiene un listado de la cantidad de vacantes de {@link crm.entities.OfertaLaboralUsmempleo} según el
     * {@link crm.entities.TipoOferta}, realizando una busqueda según un año y tipoVigencia especificados como
     * parametros.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.TipoOferta}; para cada una de ellas
     *          coleccción {@Link java.util.ArrayList} que almacena el nombre del {@link crm.entities.TipoOferta} y la
     *          cantidad de vacantes de las {@link crm.entities.OfertaLaboralUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT to.nombre, " +
            "SUM(CASE WHEN o.vacantes IS NOT NULL THEN o.vacantes ELSE 0 END) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.oferta AS to " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY to.nombre " +
            "ORDER BY to.nombre")
    ArrayList<ArrayList<String>> indiceCantidadVacantesOfertaLaboralPorTipoOferta( @Param("codVigencia") String codVigencia,
                                                                                   @Param("anio") Integer anio);



    /**
     * Obtiene un listado, de manera paginada, de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo} por mes
     * de todas las {@link crm.entities.Carrera}, realizando una busqueda según un año y tipoVigencia especificados como parametros.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} por mes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT  c.nombreCarrera, " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=1 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=2 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=4 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=6 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=8 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=10 THEN 1 END) ," +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaInicio)=12 THEN 1 END) ," +
            "COUNT(1) " +
            "FROM OfertaLaboralUsmempleo As o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN oc.carrera AS c " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaInicio) = :anio " +
            "GROUP BY c.nombreCarrera  ORDER BY c.nombreCarrera",
            countQuery = "SELECT  COUNT(c.nombreCarrera) " +
                    "FROM OfertaLaboralUsmempleo As o " +
                    "JOIN o.ofertaCarreraUsmempleoList AS oc " +
                    "JOIN oc.carrera AS c " +
                    "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
                    "AND YEAR(o.fechaInicio) = :anio " +
                    "GROUP BY c.nombreCarrera")
    Page<List<String>> indiceCantidadOfertaLaboralPorCarrera( @Param("codVigencia") String codVigencia,
                                                              @Param("anio") Integer anio,
                                                              Pageable page);




    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según su demanda
     * de {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden especificados como parametros.
     *
     * @param page Limita la cantidad de elementos, y el orden ascendente/descendente de los resultados
     *             (hql no lo permite por si mismo)
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.List} por cada {@link crm.entities.Carrera}, que contiene una coleccción {@Link java.util.List}
     *          que almacena el nombre de la {@link crm.entities.Carrera} y la cantidad de
     *          {@link crm.entities.OfertaCarreraUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c.nombreCarrera, " +
            "COUNT (o) " +
            "FROM OfertaLaboralUsmempleo As o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN oc.carrera AS c " +
            "WITH YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY c.codCarrera ")
    ArrayList<ArrayList<String>> rankingCarrerasPorDemanda( @Param("anio") Integer anio,
                                                            Pageable page);




    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según la cantidad
     * de vacantes declaradas en {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden
     * especificados como parametros.
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page Limita la cantidad de elementos, y el orden ascendente/descendente de los resultados
     *             (hql no lo permite por si mismo)
     *
     * @return Coleccion {@Link java.util.ArrayList} por cada {@link crm.entities.Carrera}, que contiene una coleccción {@Link java.util.ArrayList}
     *         que almacena el nombre de la {@link crm.entities.Carrera} y la cantidad de vacantes de las
     *          {@link crm.entities.OfertaCarreraUsmempleo} asociadas
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT c.nombreCarrera, " +
            "SUM (o.vacantes) " +
            "FROM OfertaLaboralUsmempleo As o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN oc.carrera AS c " +
            "WITH YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY c.codCarrera ")
    ArrayList<ArrayList<String>> rankingCarrerasPorVacantes( @Param("anio") Integer anio,
                                                             Pageable page);




    /**
     * Obtiene un listado con los resultados del indice de ranking de las {@link crm.entities.Carrera} según el salario
     * promedio declarado en {@link crm.entities.OfertaLaboralUsmempleo}, según el año, cantidad y orden
     * especificados como parametros.
     *
     * @param page Utilizado para limitar la cantidad de elementos, y el orden ascendente/descendente de los resultados
     *             (hql no lo permite por si mismo)
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.List} por cada {@link crm.entities.Carrera}, que contiene una coleccción
     *         {@Link java.util.List} que almacena el nombre de la {@link crm.entities.Carrera}, el salario promedio,
     *         los salarios promedios según {@Link java.util.TipoOferta} y la cantidad de
     *         {@link crm.entities.OfertaCarreraUsmempleo} asociadas, ordenadas según el promedio de salario
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query ("SELECT c.nombreCarrera, " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 0 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 1 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 2 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 3 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 4 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 5 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 6 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 7 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 8 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND( AVG (CASE WHEN o.oferta.codigo = 9 THEN o.salario ELSE 0 END) ) AS long) , " +
            "CAST ( ROUND(AVG (o.salario)) AS long ), " +
            "COUNT (o) " +
            "FROM OfertaLaboralUsmempleo As o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN oc.carrera AS c " +
            "WITH YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY c.codCarrera " +
            "HAVING ROUND(AVG (o.salario)) >= :rangoInicial " +
            "AND ROUND(AVG (o.salario)) <= :rangoFinal ")
    ArrayList<ArrayList<String>> indiceRankingCarrerasPorSalario( @Param("anio") Integer anio,
                                                                  @Param("rangoInicial") Double rangoInicial,
                                                                  @Param("rangoFinal") Double rangoFinal,
                                                                  Pageable page);



    /**
     * Obtiene un listado, de manera paginada, de la cantidad de vacantes de la {@link crm.entities.OfertaLaboralUsmempleo}
     * por mes, de todas las {@link crm.entities.Carrera}, realizando una busqueda según su año de inicio y tipoVigencia
     * especificados como parametros.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     * @param page datos que permiten la paginación.
     *
     * @return Coleccion {@Link java.util.Page} de {@Link java.util.List}, contenedora del nombre de la carrera y la
     *          cantidad de vacantes de {@link crm.entities.OfertaLaboralUsmempleo} por mes
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          coleccion {@Link java.util.List}, contenedora de la cantidad de vacantes de las
     *          {@link crm.entities.OfertaLaboralUsmempleo} para cada mes.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT  c.nombreCarrera, " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=1 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=2 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=3 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=4 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=5 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=6 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=7 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=8 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=9 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=10 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=11 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=12 THEN o.vacantes ELSE 0  END) , " +
            "SUM(o.vacantes) " +
            "FROM OfertaLaboralUsmempleo As o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN oc.carrera AS c " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY c.nombreCarrera " +
            "ORDER BY c.nombreCarrera",
            countQuery = "SELECT  COUNT(c.nombreCarrera) " +
                    "FROM OfertaLaboralUsmempleo As o " +
                    "JOIN o.ofertaCarreraUsmempleoList AS oc " +
                    "JOIN oc.carrera AS c " +
                    "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
                    "AND YEAR(o.fechaPublicacion) = :anio " +
                    "GROUP BY c.nombreCarrera  " +
                    "ORDER BY c.nombreCarrera")
    Page<List<String>> indiceCantidadVacantesOfertaLaboralPorCarrera( @Param("codVigencia") String codVigencia,
                                                                      @Param("anio") Integer anio,
                                                                      Pageable page);




    /**
     * Obtiene un listado por mes de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  String contenedor de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo} por mes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT   COUNT(CASE WHEN MONTH(o.fechaPublicacion)=1 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=2 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=3 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=4 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=5 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=6 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=7 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=8 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=9 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=10 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=11 THEN 1 END) , " +
            "COUNT(CASE WHEN MONTH(o.fechaPublicacion)=12 THEN 1 END) ,  " +
            "COUNT(1) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "WHERE TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio ")
    String indiceCantidadOfertasEmpleoPorMes(@Param("codVigencia") String codVigencia,
                                             @Param("anio") Integer anio);




    /**
     * Obtiene un listado por región de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según el estado de vigencia de la {@link crm.entities.OfertaLaboralUsmempleo} y el año
     * de publicación de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las regiones; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} con el nombre de la region y la cantidad de
     *          {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r.nombre, " +
            "COUNT( o ) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.region AS r " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY r.nombre " +
            "ORDER BY r.nombre")
    ArrayList<ArrayList<String>> indiceCantidadOfertasEmpleoPorRegion(@Param("codVigencia") String codVigencia,
                                                                      @Param("anio") Integer anio);




    /**
     * Obtiene un listado por {@link crm.entities.TipoOferta} de la cantidad de {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según el estado de vigencia de la {@link crm.entities.OfertaLaboralUsmempleo} y el año
     * de publicación de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de los {@link crm.entities.TipoOferta} ; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} con el nombre del {@link crm.entities.TipoOferta} y la cantidad de
     *          {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT to.nombre, " +
            "COUNT( o ) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.oferta AS to " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY to.nombre " +
            "ORDER BY to.nombre")
    ArrayList<ArrayList<String>> indiceCantidadOfertasEmpleoPorTipoOferta(@Param("codVigencia") String codVigencia,
                                                                          @Param("anio") Integer anio);



    /**
     * Obtiene un listado por mes de la cantidad de vacantes de las {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  Array de String contenedor de la cantidad de vacantes de {@link crm.entities.OfertaLaboralUsmempleo} por mes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT  SUM(CASE WHEN MONTH(o.fechaPublicacion)=1 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=2 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=3 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=4 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=5 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=6 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=7 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=8 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=9 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=10 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=11 THEN o.vacantes ELSE 0  END) , " +
            "SUM(CASE WHEN MONTH(o.fechaPublicacion)=12 THEN o.vacantes ELSE 0  END) , " +
            "SUM(o.vacantes) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "WHERE TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio ")
    String indiceCantidadVacantesOfertasEmpleoPorMes(@Param("codVigencia") String codVigencia,
                                                     @Param("anio") Integer anio);



    /**
     * Obtiene un listado por {@link crm.entities.TipoOferta} de la cantidad de vacantes de las {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según el estado de vigencia de la {@link crm.entities.OfertaLaboralUsmempleo} y el año
     * de publicación de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las {@link crm.entities.TipoOferta}; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} con el nombre del {@link crm.entities.TipoOferta} y la cantidad de vacantes de las
     *          {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT  r.nombre," +
            "SUM(CASE WHEN o.vacantes IS NOT NULL THEN o.vacantes ELSE 0 END) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.region AS r " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY r.nombre " +
            "ORDER BY r.nombre")
    ArrayList<ArrayList<String>> indiceCantidadVacantesOfertasEmpleoPorRegion(@Param("codVigencia") String codVigencia,
                                                                              @Param("anio") Integer anio);



    /**
     * Obtiene un listado por región de la cantidad de vacantes de las {@link crm.entities.OfertaLaboralUsmempleo},
     * realizando una busqueda según el estado de vigencia de la {@link crm.entities.OfertaLaboralUsmempleo} y el año
     * de publicación de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las regiones; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} con el nombre de la region y la cantidad de vacantes de las
     *          {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT  to.nombre," +
            "SUM(CASE WHEN o.vacantes IS NOT NULL THEN o.vacantes ELSE 0 END) " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "RIGHT JOIN o.oferta AS to " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "AND YEAR(o.fechaPublicacion) = :anio " +
            "GROUP BY to.nombre " +
            "ORDER BY to.nombre")
    ArrayList<ArrayList<String>> indiceCantidadVacantesOfertasEmpleoPorTipoOferta(@Param("codVigencia") String codVigencia,
                                                                                  @Param("anio") Integer anio);

    /**
     * Funcion que cuenta la cantidad de vacantes que hay en las ofertas laborales
     *
     * @return cantidad de vacantes
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT SUM(CASE WHEN o.vacantes IS NOT NULL THEN o.vacantes ELSE 0 END) "+
            "FROM OfertaLaboralUsmempleo as o")
    Long contarVacantes();

    /**
     * Funcion que cuenta las ofertas laborales de cada tipo de vigencia
     *
     * @return String de la forma vigentes, no_vigentes, por_validar
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(CASE WHEN o.vigencia.codVigencia = 1 THEN 1 END), " +
            "COUNT(CASE WHEN o.vigencia.codVigencia = 2 THEN 1 END), " +
            "COUNT(CASE WHEN o.vigencia.codVigencia = 4 THEN 1 END) " +
            "FROM OfertaLaboralUsmempleo o")
    String contarOfertasPorVigencia();





    /**
     * Retorna un listado de {@link crm.entities.OfertaCarreraUsmempleo} de la tabla empleo.oferta_carrera_usmempleo
     * según una {@link crm.entities.Carrera} especifica y la vigencia del @link UsuarioEmpresaUsmempleo} que crea/publica la
     * {@link OfertaLaboralUsmempleo}
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} que se desea obtener {@link crm.entities.OfertaCarreraUsmempleo}
     * @param codVigencia Tipo de Vigencia de la {@link crm.entities.UsuarioEmpresaUsmempleo} creador o publicador de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o " +
            "FROM OfertaCarreraUsmempleo AS oc " +
            "JOIN oc.ofertaLaboralUsmempleo AS o " +
            "JOIN o.adminOfertaLaboralUsmempleoList AS a " +
            "JOIN a.usuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
            "WHERE oc.carrera.codCarrera = :codCarrera " +
            "AND uue.vigencia.codVigencia = :codVigencia " +
            "ORDER BY oc.ofertaLaboralUsmempleo.fechaPublicacion ASC")
    List<OfertaLaboralUsmempleo> buscarPorCodCarreraYVigenciaUsuarioEmpresaUsmempleoOrdenPorFechaPublicacion(@Param("codCarrera") Long codCarrera, @Param("codVigencia") Short codVigencia);




    /**
     * Retorna un listado de {@link crm.entities.OfertaLaboralUsmempleo} según una {@link crm.entities.Carrera} especifica
     *
     * @param codCarrera Identificador de las {@link crm.entities.Carrera} desde las cuales obtener la
     * {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "WHERE oc.carrera.codCarrera = :codCarrera " +
            "ORDER BY o.fechaPublicacion ASC")
    List<OfertaLaboralUsmempleo> buscarPorCodCarreraOrdenPorFechaPublicacion(@Param("codCarrera") Long codCarrera);





    /**
     * Retorna un listado de {@link crm.entities.OfertaLaboralUsmempleo} según una {@link crm.entities.Carrera} especifica
     * y un rango de fechas en las que fueron publicadas
     *
     * @param codCarrera Identificador de las {@link crm.entities.Carrera} desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param fechaDesde Fecha de inicio de la publicacion desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param fechaHasta Fecha de fin de la publicacion desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "WHERE oc.carrera.codCarrera = :codCarrera " +
                "AND o.fechaPublicacion >= :fechaDesde " +
                "AND o.fechaPublicacion <= :fechaHasta " +
            "ORDER BY o.fechaPublicacion ASC")
    List<OfertaLaboralUsmempleo> buscarPorCodCarreraYFechaDesdeYFechaHastaOrdenPorFechaPublicacion(@Param("codCarrera") Long codCarrera, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);






    /**
     * Retorna un listado de {@link crm.entities.OfertaLaboralUsmempleo} según una {@link crm.entities.Carrera} especifica,
     * un rango de fechas en las que fueron publicadas y unos {@link crm.entities.CompetenciaUsmempleo} especificados
     * como parametro
     *
     * @param codCarrera Identificador de las {@link crm.entities.Carrera} desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param fechaDesde Fecha de inicio de la publicacion desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param fechaHasta Fecha de fin de la publicacion desde las cuales obtener la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param idsCompetencias Lista con los id de {@link crm.entities.CompetenciaUsmempleo} que deben tener las {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o " +
            "FROM OfertaLaboralUsmempleo AS o " +
            "JOIN o.ofertaCarreraUsmempleoList AS oc " +
            "JOIN o.competenciaOfertaLaboralList AS compOfertaLaboral " +
            "JOIN compOfertaLaboral.nivelCompetenciaUsmempleo AS n " +
            "JOIN n.competenciaUsmempleo AS compUsmempleo " +
            "WHERE oc.carrera.codCarrera = :codCarrera " +
                "AND o.fechaPublicacion >= :fechaDesde " +
                "AND o.fechaPublicacion <= :fechaHasta " +
                "AND compUsmempleo.id IN (:idsCompetencias) " +
            "ORDER BY o.fechaPublicacion ASC")
    List<OfertaLaboralUsmempleo> buscarPorCodCarreraYFechaDesdeYFechaHastaYIdCompetenciaOrdenPorFechaPublicacion(@Param("codCarrera") Long codCarrera, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("idsCompetencias") List<Short> idsCompetencias);

    /**
     * Retorna un listado de {@link crm.entities.OfertaLaboralUsmempleo} que tengan un idEmpresa igual
     * al id otorgado como parametro
     *
     * @param idEmpresa Identificador de la {@link crm.entities.Empresa}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT o FROM OfertaLaboralUsmempleo AS o WHERE o.empresa.id = :idEmpresa ")
    List<OfertaLaboralUsmempleo> buscarPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);
}
