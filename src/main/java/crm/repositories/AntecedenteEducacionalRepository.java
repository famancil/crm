package crm.repositories;

import crm.entities.AntecedenteEducacional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.AntecedenteEducacional}.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface AntecedenteEducacionalRepository extends CrudRepository<AntecedenteEducacional, Long> {

    /**
     * Retorna una lista con los antecedentes educacionales de una persona que tengan como institucion a la USM.
     *
     * @param cod cod  de la institucion que se desea buscar.
     * @param idUsuario id del usuario del que se quieren encontrar los antecedentes.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.AntecedenteEducacional} de un usuario cuya institucion
     * sea la USM.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteEducacional a WHERE a.institucion.codInstitucion = :cod AND a.usuario.id = :idUsuario ORDER BY a.anioIngreso asc")
    List<AntecedenteEducacional> findByInstitucionUsm(@Param("cod") Short cod, @Param("idUsuario") Long idUsuario);

    /**
     * Retorna una lista con los antecedentes educacionales de una persona que NO tengan como institucion a la USM.
     *
     * @param cod cod  de la institucion que se desea buscar.
     * @param idUsuario id del usuario del que se quieren encontrar los antecedentes.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.AntecedenteEducacional} de un usuario cuya institucion
     * NO sea la USM.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteEducacional a WHERE a.institucion.codInstitucion != :cod AND a.usuario.id = :idUsuario ORDER BY a.anioIngreso asc")
    List<AntecedenteEducacional> findByInstitucion(@Param("cod") Short cod,@Param("idUsuario") Long idUsuario);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion hasta cierto año
     *
     * @param paisId id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anio año hasta donde se quiere calcular la cantidad de exalumnos.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion hasta un año determinado.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :paisId AND cod_institucion = :codInstitucion AND (antedu_año_titulo <= :anio OR antedu_año_egreso <= :anio)", nativeQuery = true)
    Integer getCantidadExalumnosRangoAniosInstitucion(@Param("paisId") Short paisId,@Param("codInstitucion") Short codInstitucion,@Param("anio") Integer anio);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion hasta cierto año en una carrera determinada
     *
     * @param paisId id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anio año hasta donde se quiere calcular la cantidad de exalumnos.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera hasta un año determinado.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :paisId AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND (antedu_año_titulo <= :anio OR antedu_año_egreso <= :anio)", nativeQuery = true)
    Integer getCantidadExalumnosRangoAniosInstitucionCarrera(@Param("paisId") Short paisId,@Param("codInstitucion") Short codInstitucion,@Param("codCarrera") Long codCarrera, @Param("anio") Integer anio);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso, AnioEgreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con los
     * criterios establecidos por anioIngreso, anioEgreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_egreso = :anioEgreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioIngresoAnioEgresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioIngreso") Integer anioIngreso,@Param("anioEgreso") Integer anioEgreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por el parametro AnioIngreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con el
     * criterio establecido por anioIngreso.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioIngreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioIngreso") Integer anioIngreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por el parametro AnioEgreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con el
     * criterio establecido por anioEgreso.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_egreso = :anioEgreso", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioEgreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioEgreso") Integer anioEgreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por el parametro AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioTitulacion año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con el
     * criterio establecido por anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso y AnioEgreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con los
     * criterios establecidos por anioIngreso y anioEgreso
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_egreso = :anioEgreso", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioIngresoAnioEgreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioIngreso") Integer anioIngreso,@Param("anioEgreso") Integer anioEgreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con los
     * criterios establecidos por anioIngreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioIngresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioIngreso") Integer anioIngreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion en una carrera determinada y que cumplen con los
     * requisitos otorgados por los parametros AnioEgreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param codCarrera codigo de la carrera que se desea buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion en una carrera que cumplen con los
     * criterios establecidos por anioEgreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_carrera = :codCarrera AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_egreso = :anioEgreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosCarreraAnioEgresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("codCarrera") Integer codCarrera,@Param("anioEgreso") Integer anioEgreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso, AnioEgreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con los
     * criterios establecidos por anioIngreso, anioEgreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_egreso = :anioEgreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosAnioIngresoAnioEgresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioIngreso") Integer anioIngreso,@Param("anioEgreso") Integer anioEgreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con el
     * requisito otorgado por el parametro AnioIngreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con el
     * criterio establecido por anioIngreso
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso", nativeQuery = true)
    Integer cantidadExalumnosAnioIngreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioIngreso") Integer anioIngreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con el
     * requisito otorgado por el parametro AnioEgreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioEgreso año de ingreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con el
     * criterio establecido por anioEgreso
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_egreso = :anioEgreso", nativeQuery = true)
    Integer cantidadExalumnosAnioEgreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioEgreso") Integer anioEgreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con el
     * requisito otorgado por el parametro AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioTitulacion año de ingreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con el
     * criterio establecido por anioTitulacion
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso y AnioEgreso
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con los
     * criterios establecidos por anioIngreso y anioEgreso
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_egreso = :anioEgreso", nativeQuery = true)
    Integer cantidadExalumnosAnioIngresoAnioEgreso(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioIngreso") Integer anioIngreso,@Param("anioEgreso") Integer anioEgreso);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con los
     * requisitos otorgados por los parametros AnioIngreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioIngreso año de ingreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con los
     * criterios establecidos por anioIngreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_ingreso = :anioIngreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosAnioIngresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioIngreso") Integer anioIngreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Retorna la cantidad de exalumnos que tiene una institucion y que cumplen con los
     * requisitos otorgados por los parametros AnioEgreso y AnioTitulacion
     *
     * @param idPais id del pais de la institucion.
     * @param codInstitucion cod de la institucion que se desea buscar.
     * @param anioEgreso año de egreso de los exalumnos que se quieren buscar.
     * @param anioTitulacion  año de titulacion de los exalumnos que se quieren buscar.
     *
     * @return Cantidad (@link java.lang.Integer} de exalumnos que tiene la institucion que cumplen con los
     * criterios establecidos por anioEgreso y anioTitulacion.
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(DISTINCT(a.usuaex_id)) FROM antecedente_educacional a WHERE cod_pais = :idPais AND cod_institucion = :codInstitucion AND cod_estudio IN (1,2,3,4,5,6) AND cod_estado_estudio IN (6,9,10,11) AND antedu_año_egreso = :anioEgreso AND antedu_año_titulo = :anioTitulacion", nativeQuery = true)
    Integer cantidadExalumnosAnioEgresoAnioTitulacion(@Param("idPais")Integer idPais,@Param("codInstitucion") Integer codInstitucion,@Param("anioEgreso") Integer anioEgreso,@Param("anioTitulacion") Integer anioTitulacion);

    /**
     * Actualiza el id de usuario de un {@link crm.entities.AntecedenteEducacional} que posea como id de usuario  el id2.
     *
     * @param id1 Id de usuario por el cual se cambiara el id del usuario del {@link crm.entities.AntecedenteEducacional} actual.
     * @param id2 Id del usuario antiguo que poseia el {@link crm.entities.AntecedenteEducacional}
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AntecedenteEducacional a set a.usuario.id= :id1 where a.usuario.id = :id2")
    @Transactional
    void actualizarAntecedenteEducacional(@Param("id1") Long id1, @Param("id2") Long id2);




    /**
     * Obtiene un listado, de manera paginada, de la cantidad de curriculos ( curriculo es equivalente a
     * {@link crm.entities.Usuario} ) según el {@link crm.entities.TipoEstadoEstudio}, clasificados por
     * {@link crm.entities.Carrera}
     *
     * @param page datos que permiten la paginación.
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          coleccion {@Link java.util.List}, contenedora del nombre de la carrera y la cantidad de curriculos
     *          ( {@link crm.entities.Usuario} ) asociados.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT c.nombreCarrera, " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 0 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 1 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 2 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 4 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 5 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 6 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 7 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 8 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 9 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 10 THEN 1 END), " +
                    "COUNT(CASE WHEN tee.codEstadoEstudio = 11 THEN 1 END), " +
                    "COUNT(1) " +
                "FROM AntecedenteEducacional AS a " +
                "JOIN a.carrera AS c " +
                "RIGHT JOIN a.tipoEstadoEstudio AS tee " +
                "WHERE a.institucion.codInstitucion = :codInstitucion " +
                "GROUP BY c.nombreCarrera " +
                "ORDER BY c.nombreCarrera",
                countQuery = "SELECT  COUNT(c.nombreCarrera) " +
                            "FROM AntecedenteEducacional AS a " +
                            "JOIN a.carrera AS c " +
                            "RIGHT JOIN a.tipoEstadoEstudio AS tee " +
                            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                            "GROUP BY c.nombreCarrera " +
                            "ORDER BY c.nombreCarrera")
    Page<List<String>> indiceCurriculosPorCarrera(@Param("codInstitucion") Short codInstitucion,
                                                  Pageable page );




    /**
     * Obtiene un listado de la cantidad de curriculos ( curriculo es equivalente a {@link crm.entities.Usuario} )
     * segmentado por {@link crm.entities.Region} del {@link crm.entities.Usuario}
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las {@link crm.entities.Region}; para cada una de ellas
     *          se tiene una coleccion {@Link java.util.ArrayList}, contenedora del nombre de la Region y la cantidad de curriculos
     *          asociados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT r.nombre, " +
                        "COUNT(DISTINCT u) " +
                "FROM AntecedenteEducacional AS a " +
                "JOIN a.usuario AS u " +
                "JOIN u.comuna AS c " +
                "JOIN c.provincia AS p " +
                "RIGHT JOIN p.region AS r " +
                "WITH a.institucion.codInstitucion = :codInstitucion " +
                    "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera  " +
                "GROUP BY r.nombre " +
                "ORDER BY r.nombre" )
    ArrayList<ArrayList<String>> indiceCantidadCurriculosPorRegion ( @Param("codInstitucion") Short codInstitucion,
                                                                        @Param("codCarrera") String codCarrera );





    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) segmentado por el
     * nivel de conocimiento del {@link crm.entities.Idioma} registrado en {@link crm.entities.ManejoIdioma},
     * según la {@link crm.entities.Carrera}, la {@link crm.entities.Institucion} y el {@link crm.entities.Idioma}
     * especificados como parametros para la busqueda
     *
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     * @param codIdioma Identificador del {@link crm.entities.Idioma}
     *
     * @return  Array de String contenedora del nombre del {@link crm.entities.Idioma}, y la cantidades de
     *          {@link crm.entities.Usuario} para cada nivel de Conversacion, Escritura y Traduccion
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT i.nomIdioma, " +
                "COUNT (CASE WHEN mi.nivelConversacion = 1 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelConversacion = 2 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelConversacion = 3 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelConversacion IS NULL THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelEscritura = 1 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelEscritura = 2 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelEscritura = 3 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelEscritura IS NULL THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelTraduccion = 1 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelTraduccion = 2 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelTraduccion = 3 THEN 1 END ), " +
                "COUNT (CASE WHEN mi.nivelTraduccion IS NULL THEN 1 END ), " +
                "COUNT (u) " +
            "FROM AntecedenteEducacional AS a " +
            "JOIN a.usuario AS u " +
            "JOIN u.manejoIdiomasList AS mi " +
            "JOIN mi.idioma AS i " +
            "WHERE a.institucion.codInstitucion = :codInstitucion " +
                "AND TO_CHAR(a.carrera.codCarrera, 'FM9999')  LIKE :codCarrera " +
                "AND i.codIdioma = :codIdioma " +
            "GROUP BY i.nomIdioma " )
    String indicePersonasPorNivelIdioma(@Param("codInstitucion") Short codInstitucion,
                                        @Param("codCarrera") String codCarrera,
                                        @Param("codIdioma") Short codIdioma);




    /**
     * Retorna una List con {@link crm.entities.AntecedenteEducacional} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.AntecedenteEducacional} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteEducacional AS a WHERE a.usuario.id = :idUsuario ")
    List<AntecedenteEducacional> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Obtiene un listado de  {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} asociada a {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a " +
            "FROM AntecedenteEducacional AS a " +
            "WHERE a.carrera.codCarrera = :codCarrera ")
    List<AntecedenteEducacional> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Obtiene un listado de  {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Institucion}
     *
     * @param codInstitucion id de la {@link crm.entities.Institucion} asociada {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a " +
            "FROM AntecedenteEducacional AS a " +
            "WHERE a.institucion.codInstitucion = :codInstitucion ")
    List<AntecedenteEducacional> buscarPorCodInstitucion(@Param("codInstitucion") Short codInstitucion);




    /**
     * Obtiene un listado de  {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Institucion}
     * y un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} asociada a {@link crm.entities.AntecedenteEducacional}
     * @param codInstitucion id de la {@link crm.entities.Institucion} asociada {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT a " +
            "FROM AntecedenteEducacional AS a " +
            "WHERE a.institucion.codInstitucion = :codInstitucion " +
            "AND a.carrera.codCarrera = :codCarrera ")
    List<AntecedenteEducacional> buscarPorCodInstitucionYCodCarrera(@Param("codInstitucion") Short codInstitucion, @Param("codCarrera") Long codCarrera);





    /**
     * Obtiene la cantidad de {@link crm.entities.AntecedenteEducacional}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} de las ante{@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(a) FROM AntecedenteEducacional AS a WHERE a.carrera.codCarrera = :codCarrera ")
    Long buscarCantidadPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.AntecedenteEducacional}
     *
     * @param idAntecedenteEducacionalBuscar Id del {@link crm.entities.AntecedenteEducacional} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE AntecedenteEducacional AS a " +
                    "SET a.usuario.id = :idUsuarioSetear " +
                    "WHERE a.id = :idAntecedenteEducacionalBuscar")
    void actualizarUsuarioId(@Param("idAntecedenteEducacionalBuscar") Long idAntecedenteEducacionalBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Elimina un {@link crm.entities.AntecedenteEducacional} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.AntecedenteEducacional}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM AntecedenteEducacional AS a " +
                    "WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);




    /**
     * Obtiene un {@link crm.entities.AntecedenteEducacional} según su id
     *
     * @param id id del {@link crm.entities.AntecedenteEducacional}
     *
     * @return  {@link crm.entities.AntecedenteEducacional} buscado
     *
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT a FROM AntecedenteEducacional AS a " +
            "WHERE a.id = :id ")
    AntecedenteEducacional getAntecedenteEducacionalById(@Param("id") Long id);
}
