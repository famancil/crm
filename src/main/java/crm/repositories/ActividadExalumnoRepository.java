package crm.repositories;

import crm.entities.ActividadExalumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.ActividadExalumno}.
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface ActividadExalumnoRepository  extends CrudRepository<ActividadExalumno, Long> {
    /**
     * Devuelve una lista de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     * que posean un usuarioUsmempleoEmpresa.empresa.id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado historico de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} que corresponden
     * a contactos historicos con la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT ae FROM ActividadExalumno AS ae JOIN ae.sucursalEmpresa AS se JOIN ae.usuario AS u WHERE ae.empresa.id = :idEmpresa ORDER BY ae.usuario.apellidoPaterno")
    List<ActividadExalumno> getActividadExalumnosEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcla la cantidad de de {@link crm.entities.CompromisoSocio} vigentes
     * que posea un {@Link crm.entities.Usuario}
     *
     * @param idUsuario id del Usuario al cual se le requiere calcular la cantidad de compromisos vigentes
     * @return Cantidad de {@link crm.entities.CompromisoSocio} que tiene el {@Link crm.entities.Usuario}
     * con id idUsuario.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT COUNT(cs) FROM aexa.compromiso_socio cs WHERE usuaex_id = :idUsuario AND cod_vigencia = 1", nativeQuery = true)
    Integer getCompromisosVigentes(@Param("idUsuario") Long idUsuario);



    /**
     * Devuelve una lista de {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante}
     * que posean un usuarioUsmempleoEmpresa.empresa.id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado historico de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.ContactoHistoricoEmpresaPersonaParticipante} que corresponden
     * a contactos historicos con la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT ae FROM ActividadExalumno AS ae JOIN ae.sucursalEmpresa AS se JOIN ae.usuario AS u WHERE ae.empresa.id = :idEmpresa ORDER BY ae.usuario.apellidoPaterno",
            countQuery = "SELECT COUNT(ae) FROM ActividadExalumno AS ae JOIN ae.sucursalEmpresa AS se JOIN ae.usuario AS u WHERE ae.empresa.id = :idEmpresa")
    Page<ActividadExalumno> getActividadExalumnosEmpresaPrimeros20(@Param("idEmpresa")Long idEmpresa, Pageable page);




    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoSector} (rubro) de
     * la {@link crm.entities.Empresa}, según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     * (Se considera contado de usuario de una vez por empresa . Si posee varios  {@link crm.entities.ActividadExalumno}
     * asociados a una misma {@link crm.entities.Empresa} se cuenta solamente una vez)
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Colleccion {@Link java.util.ArrayList} segmentado por {@link crm.entities.TipoSector}; para cada uno de ellos
     *          colleccion {@Link java.util.ArrayList} contenedora del Id del {@link crm.entities.TipoSector} y la cantidad
     *          asociada
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    // Retorna (cod_sector, cantidad de usuarios por sector -contando al usuario solamente una vez por empresa-)
    @Query(value = "SELECT resultado.cod_sector, SUM(resultado.cantidadUsuarios) " +
                    "FROM (SELECT p.cod_sector, COUNT (DISTINCT act.usuaex_id) AS cantidadUsuarios " +
                            "FROM org.tipo_sector AS ts " +
                            "JOIN org.perfil_empresa_usmempleo AS p " +
                            "ON  ts.cod_sector = p.cod_sector " +
                            "JOIN actividad_exalumno AS act " +
                            "ON p.perempusm_id = act.perempusm_id " +
                            "JOIN antecedente_educacional AS ant " +
                            "ON act.usuaex_id = ant.usuaex_id " +
                            "WHERE TO_CHAR(ant.cod_carrera, 'FM9999') LIKE :codCarrera " +
                                "AND ant.cod_institucion = :codInstitucion " +
                                "AND (act.actexa_fecha_egreso IS NULL OR act.actexa_fecha_egreso > CURRENT_DATE)" +
                            "GROUP BY p.perempusm_id) AS resultado " +
                    "GROUP BY resultado.cod_sector " +
                    "ORDER BY resultado.cod_sector",
            nativeQuery = true)
    ArrayList<ArrayList<String>> indiceCantidadPersonasConTrabajoPorTipoRubro (@Param("codInstitucion") Short codInstitucion,
                                                                                @Param("codCarrera") String codCarrera);




    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el {@link crm.entities.TipoCargo},
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion} especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return Coleccion {@Link java.util.List} contenedora de los {@link crm.entities.TipoCargo}; para cada una de ellos
     *          coleccion {@Link java.util.List}, contenedora del nombre del  {@link crm.entities.TipoCargo} y la cantidad
     *          de personas asociados.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT tc.nomCargo, " +
                    "COUNT(act) " +
            "FROM ActividadExalumno AS act " +
            "JOIN act.tipoCargo AS tc " + 
            "JOIN act.usuario AS u " +
            "JOIN u.antecedenteEducacionalList AS ant " +
            "WHERE ant.institucion.codInstitucion = :codInstitucion " +
                "AND TO_CHAR(ant.carrera.codCarrera, 'FM9999')  LIKE :codCarrera " +
                "AND ( act.fechaEgreso IS NULL OR act.fechaEgreso > CURRENT_DATE )" +
            "GROUP BY tc.nomCargo " +
            "ORDER BY tc.nomCargo")
    ArrayList<ArrayList<String>> indicePersonasConTrabajoPorTipoCargo (@Param("codInstitucion") Short codInstitucion,
                                                                        @Param("codCarrera") String codCarrera);




    /**
     * Obtiene un listado de la cantidad de personas ( {@link crm.entities.Usuario} ) con trabajo,
     * registrado en sus {@link crm.entities.ActividadExalumno}, segmentado por el tramo de remuneración registrado,
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion} especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     *
     * @return String ontenedora del nombre del {@link crm.entities.TipoCargo} y la cantidad
     *          de personas asociados.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(CASE WHEN act.remuneracion >= 0 AND act.remuneracion < 172000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 172000 AND act.remuneracion < 300000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 300000 AND act.remuneracion < 600000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 600000 AND act.remuneracion < 900000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 900000 AND act.remuneracion < 1200000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 1200000 AND act.remuneracion < 1500000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 1500000 AND act.remuneracion < 1800000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 1800000 AND act.remuneracion < 2100000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion >= 2100000 THEN 1 END), " +
                    "COUNT(CASE WHEN act.remuneracion IS NULL THEN 1 END), " +
                    "COUNT(1) " +
            "FROM ActividadExalumno AS act " +
            "JOIN act.tipoCargo AS tc " +
            "JOIN act.usuario AS u " +
            "JOIN u.antecedenteEducacionalList AS ant " +
            "WHERE ant.institucion.codInstitucion = :codInstitucion " +
                "AND TO_CHAR(ant.carrera.codCarrera, 'FM9999')  LIKE :codCarrera " +
                "AND ( act.fechaEgreso IS NULL OR act.fechaEgreso > CURRENT_DATE )" )
    String indicePersonasConTrabajoPorTramoIngreso (@Param("codInstitucion") Short codInstitucion,
                                                    @Param("codCarrera") String codCarrera);




    /**
     * Obtiene un listado con el ranking de las {@link crm.entities.Empresa} con mayor cantidad de personas ( {@link crm.entities.Usuario} )
     * según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * TODO revisar el tema de alto ejecutivo
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     * @param page Utilizado para limitar la cantidad de elementos, y el orden ascendente/descendente de los resultados
     *
     * @return Coleccion {@Link java.util.List} contenedora de las {@link crm.entities.Empresa}; para cada una de ellos
     *          coleccion {@Link java.util.List}, contenedora de la cantidad de {@link crm.entities.Usuario}
     *          contratados, cantidad de altos ejecutivos, cantidad de hombres, cantidad de mujeres
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT e.nombreFantasiaEmpresa, " +
                "COUNT(u) AS contratados, " +
                "COUNT(CASE WHEN act.tipoCargo.nivCargo = 'Altos Ejecutivos' THEN 1 END) AS altos_ejecutivos, " +
                "COUNT(CASE WHEN u.sexo = 0 THEN 1 END) AS hombres, " +
                "COUNT(CASE WHEN u.sexo = 1 THEN 1 END) AS mujeres " +
            "FROM ActividadExalumno AS act " +
            "JOIN act.empresa AS e " +
            "JOIN act.usuario AS u " +
            "JOIN u.antecedenteEducacionalList AS ant " +
            "WHERE ant.institucion.codInstitucion = :codInstitucion " +
                "AND TO_CHAR(ant.carrera.codCarrera, 'FM9999')  LIKE :codCarrera " +
                "AND YEAR(act.fechaIngreso) = :anio " +
            "GROUP BY e ")
    ArrayList<ArrayList<String>> indiceRankingEmpresasMasPersonasContratadas (  @Param("codInstitucion") Short codInstitucion,
                                                                                @Param("codCarrera") String codCarrera,
                                                                                @Param("anio") Integer anio,
                                                                                Pageable page);

    /**
     * Retorna una lista con los {@link crm.entities.ActividadExalumno} de una persona.
     *
     * @param idUsuario id del usuario del que se quieren encontrar los antecedentes.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.ActividadExalumno} de un usuario.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query("SELECT a FROM ActividadExalumno a WHERE a.usuario.id = :idUsuario ORDER BY a.posicionOrden desc")
    List<ActividadExalumno> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.ActividadExalumno}
     *
     * @param idActividadExalumnoBuscar Id del {@link crm.entities.ActividadExalumno} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE ActividadExalumno AS a SET a.usuario.id = :idUsuarioSetear WHERE a.id = :idActividadExalumnoBuscar")
    void actualizarUsuarioId(@Param("idActividadExalumnoBuscar") Long idActividadExalumnoBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);



    /**
     * Elimina un {@link crm.entities.ActividadExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.ActividadExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM ActividadExalumno AS a WHERE a.id = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);

    @Query("SELECT a FROM ActividadExalumno AS a WHERE a.sucursalEmpresa.sucursalCodigo = :sucursalCodigo")
    List<ActividadExalumno> getActividadExalumnosSucursalEmpresa(@Param("sucursalCodigo") Long sucursalCodigo);
}
