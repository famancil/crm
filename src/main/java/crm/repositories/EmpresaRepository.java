package crm.repositories;

import crm.entities.Empresa;
import crm.entities.SucursalEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.Empresa}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

    /**
     * Retorna la {@link crm.entities.Empresa} que posea rut igual al parametro otorgado
     *
     * @param rutEmpresa id de la empresa que se desea buscar.
     * @return {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT e FROM Empresa e WHERE e.rutEmpresa LIKE :rutEmpresa")
    Empresa buscarEmpresaPorRut(@Param("rutEmpresa") String rutEmpresa);

    /**
     * Retorna la {@link crm.entities.Empresa} que posea idEmpresaExtranjera igual al parametro otorgado
     *
     * @param idEmpresaExtranjera id extranjero de la empresa que se desea buscar.
     * @return {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT e FROM Empresa e WHERE e.idEmpresaExtranjera LIKE :idEmpresaExtranjera")
    Empresa buscarEmpresaPorIdEmpresaExtranjera(@Param("idEmpresaExtranjera") String idEmpresaExtranjera);

    /**
     * Retorna la {@link crm.entities.Empresa} que posea un id igual al parametro otorgado
     * que posean un calce en razon social, nombre fantasia o sigla con el parametro buscado
     *
     * @param idEmpresa id de la empresa que se desea buscar.
     * @return {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    Empresa findById(Long idEmpresa);




    /**
     * Retorna una lista de todas las empresas registradas en el sistema {@link crm.entities.Empresa}
     *
     * @return {@Link List} de {@link crm.entities.Empresa}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    List<Empresa> findAll();




    /**
     * Retorna un listado de {@link crm.entities.Empresa} asociadas a {@link crm.entities.ActividadExalumno},
     * que posean un calce en razon social, nombre fantasia o sigla con el parametro buscado
     *
     * @param nombre nombre de emrpesa que se desean buscar.
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT a.empresa " +
            "FROM ActividadExalumno AS a " +
            "JOIN a.empresa AS e " +
            "WHERE e.vigencia.codVigencia = 1 AND (UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "OR UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')) " +
            "ORDER BY e.razonSocial ASC")
    List<Empresa> buscarEmpresasDeActividadExalumnoPorCalceRazonSocialONombreFantasiaOSigla(@Param("nombre") String nombre);




    /**
     * Retorna un listado de {@link crm.entities.Empresa} que posean un calce en razon social, nombre fantasia o sigla con el parametro buscado
     *
     * @param nombre nombre de emrpesa que se desean buscar.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.Empresa}.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT DISTINCT e " +
            "FROM Empresa AS e " +
            "WHERE e.vigencia.codVigencia = 1 AND (UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "OR UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                "OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')) " +
            "ORDER BY e.razonSocial ASC")
    List<Empresa> buscarEmpresasPorCalceRazonSocialONombreFantasiaOSigla(@Param("nombre") String nombre);





    /**
     * Calcula la cantidad de ofertas de la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de ofertas laborales.
     * @return Valor {@link java.lang.Integer} de la cantidad de ofertas laborales de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(o) FROM OfertaLaboralUsmempleo o WHERE o.empresa.id = :idEmpresa")
    Integer calcularCantidadOfertasEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la cantidad de publicadores de la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de publicadores.
     * @return Valor {@link java.lang.Integer} de la cantidad de publicadores de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(DISTINCT u.usuarioEmpresaUsmempleo.id) FROM UsuarioUsmempleoEmpresa u WHERE u.empresa.id = :idEmpresa")
    Integer calcularCantidadPublicadoresEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la cantidad de usuarios que han tenido actividades en la {@link crm.entities.Empresa}
     * que posea un id igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de usuarios.
     * @return Valor {@link java.lang.Integer} de la cantidad de usuarios que han tenido actividades en una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(DISTINCT a.usuario.id) FROM ActividadExalumno a WHERE a.empresa.id = :idEmpresa")
    Integer calcularCantidadUsuariosEmpresa(@Param("idEmpresa") Long idEmpresa);


    /**
     * Retorna un objeto {@link crm.entities.Empresa} que posea una Razon Social, nombre de fantasia o sigla
     * identico al parametro pasado.
     *
     * @param nombre nombre de empresa que se desean buscar.
     * @return Objeto {@link crm.entities.Empresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT e FROM Empresa AS e WHERE e.vigencia.codVigencia = 1 AND (UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))  " +
            "OR UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))" +
            "OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) = UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN'))))")
    List<Empresa> buscarEmpresaEspecificaPorRazonSocialONombreFantasiaOSigla(@Param("nombre") String nombre);


    /**
     * Calcula la cantidad de correos que existen entre la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado y la red de exalumnos
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de correos.
     * @return Valor {@link java.lang.Integer} de la cantidad de correos entre una empresa y la red.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "select count(c) from org.contacto_historico_empresa c join org.contacto_historico_empresa_persona_participante cp on c.conhisemp_id=cp.conhisemp_id join org.usuario_usmempleo_empresa u on u.usuempempusm_id = cp.usuempempusm_id  where cod_contacto = 2 and perempusm_id=:idEmpresa",nativeQuery = true)
    Integer calcularCorreosEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la cantidad reuniones que han habido entre la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado y la red de exalumnos.
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de reuniones.
     * @return Valor {@link java.lang.Integer} de la cantidad de reuniones entre una empresa y la red.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "select count(c) from org.contacto_historico_empresa c join org.contacto_historico_empresa_persona_participante cp on c.conhisemp_id=cp.conhisemp_id join org.usuario_usmempleo_empresa u on u.usuempempusm_id = cp.usuempempusm_id  where cod_contacto = 1 and perempusm_id=:idEmpresa",nativeQuery = true)
    Integer calcularReunionesEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la cantidad de llamadas telefonicas entre la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado y la red de exalumnos.
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero de llamadas telefonicas.
     * @return Valor {@link java.lang.Integer} de la cantidad de llamadas telefonicas entre una empresa y la red.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "select count(c) from org.contacto_historico_empresa c join org.contacto_historico_empresa_persona_participante cp on c.conhisemp_id=cp.conhisemp_id join org.usuario_usmempleo_empresa u on u.usuempempusm_id = cp.usuempempusm_id  where cod_contacto in (3,4,5,6,7,8) and perempusm_id=:idEmpresa",nativeQuery = true)
    Integer calcularLlamadasEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la suma total de vacantes de todas las {@Link crm.entities.OfertaLaboralUsmempleo}
     * que corresponden a la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular la suma total de vacantes.
     * @return Valor {@link java.lang.Integer} de la suma de vacantes de todas las ofertas laborales que ha hecho una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "select sum(o.ofelabusm_vacantes) from empleo.oferta_laboral_usmempleo o where perempusm_id = :idEmpresa",nativeQuery = true)
    Integer calcularVacantesTotalesOfertasEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Calcula la cantidad total de postulantes que ha tenido la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular el numero total de postulantes.
     * @return Valor {@link java.lang.Integer} del numero total de postulantes que ha tenido una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "select count(distinct(usuaex_id)) from empleo.oferta_laboral_usmempleo o join empleo.postulacion_ofe_lab_usmempleo p on o.ofelabusm_id = p.ofelabusm_id where perempusm_id=:idEmpresa",nativeQuery = true)
    Integer calcularPostulantesTotalesOfertasEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna la fecha de la primera oferta laboral de la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular la fecha de la primera oferta.
     * @return Valor {@link java.util.Date} de la fecha de la primera oferta laboral de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT min(o.ofelabusm_fecha_publicacion) FROM empleo.oferta_laboral_usmempleo o WHERE perempusm_id = :idEmpresa",nativeQuery = true)
    Date fechaPrimeraOfertaEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna la fecha de la ultima oferta laboral de la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular la fecha de la ultima oferta.
     * @return Valor {@link java.util.Date} de la fecha de la ultima oferta laboral de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT max(o.ofelabusm_fecha_publicacion) FROM empleo.oferta_laboral_usmempleo o WHERE perempusm_id = :idEmpresa",nativeQuery = true)
    Date fechaUltimaOfertaEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Obtiene todos los paises que se encuentran en la base de datos al momento de
     * hacer la consulta.
     *
     * @return {@link java.util.List} con todas las entidades {@link crm.entities.Pais}.
     *
     * @author Renata Mella <renata.mella.12@sansano.usm.cl>
     */
    @Query(value = "SELECT e FROM Empresa e WHERE e.pais.id = :idPais")
    List<Empresa> findByidPais(@Param("idPais") Short idPais);

    /**
     * Calcula la cantidad de sucursales que corresponden a la {@link crm.entities.Empresa} que posea un id
     * igual al parametro otorgado
     *
     * @param idEmpresa id de empresa a la cual se le requiere calcular la cantidad de sucursales.
     * @return Valor {@link java.lang.Integer} de la cantidad de sucursales de una empresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT COUNT(s) FROM org.sucursal_empresa s WHERE s.perempusm_id = :idEmpresa", nativeQuery = true)
    Integer calcularCantidadSucursales(@Param("idEmpresa") Long idEmpresa);

    /**
     * Funcion que cuenta las empresas por tipo de vigencia
     *
     * @return String de la forma vigentes, no_vigentes, por_validar
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(CASE WHEN e.vigencia.codVigencia = 1 THEN 1 END), " +
            "COUNT(CASE WHEN e.vigencia.codVigencia = 2 THEN 1 END), " +
            "COUNT(CASE WHEN e.vigencia.codVigencia = 4 THEN 1 END) " +
            "FROM Empresa e")
    String contarEmpresasPorVigencia();

    /**
     * Retorna todas las empresas que han sido creadas por un alumno y que aun no han sido validadas
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT ae.empresa FROM ActividadExalumno ae JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioAexaPorValidar(Pageable page);

    /**
     * Retorna todas las empresas que han sido creadas por un alumno y que aun no han sido validadas que calcen
     * con el rut o idExtranjero entregados como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT ae.empresa FROM ActividadExalumno ae JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioAexaPorValidarPorRut(@Param("rut") String rut,@Param("idExtranjero") String idExtranjero, Pageable page);

    /**
     * Retorna todas las empresas que han sido creadas por un alumno y que aun no han sido validadas que calcen
     * con el nombre entregado como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT ae.empresa FROM ActividadExalumno ae "+
            "JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae "+
                    "JOIN ae.empresa e WHERE e.vigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioAexaPorValidarPorNombre(@Param("nombre") String nombre, Pageable page);

    /**
     * Retorna todas las empresas que han sido creadas por un publicador y que aun no han sido validadas
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidar(Pageable page);

    /**
     * Retorna todas las empresas que han sido creadas por un publicador y que aun no han sido validadas que calcen
     * con el rut o idExtranjero entregados como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidarPorRut(@Param("rut") String rut,@Param("idExtranjero") String idExtranjero, Pageable page);

    /**
     * Retorna todas las empresas que han sido creadas por un publicador y que aun no han sido validadas que calcen
     * con el nombre entregado como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue "+
            "JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue "+
                    "JOIN uue.empresa e WHERE e.vigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))")
    Page<Empresa> buscarEmpresasCreadasPorUsuarioEmpresaUsmempleoPorValidarPorNombre(@Param("nombre") String nombre, Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un alumno y que aun no han sido validadas
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT ae.empresa FROM ActividadExalumno ae JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidar(Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un alumno y que aun no han sido validadas que calcen
     * con el rut o idExtranjero entregados como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT ae.empresa FROM ActividadExalumno ae JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidarPorRut(@Param("rut") String rut,@Param("idExtranjero") String idExtranjero, Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un alumno y que aun no han sido validadas que calcen
     * con el nombre entregado como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT ae.empresa FROM ActividadExalumno ae "+
            "JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))",
            countQuery = "SELECT DISTINCT COUNT(ae.empresa) FROM ActividadExalumno ae "+
                    "JOIN ae.sucursalEmpresa s JOIN ae.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioAexaPorValidarPorNombre(@Param("nombre") String nombre, Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un publicador y que aun no han sido validadas
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidar(Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un publicador y que aun no han sido validadas que calcen
     * con el rut o idExtranjero entregados como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (e.rutEmpresa LIKE :rut OR e.idEmpresaExtranjera LIKE :idExtranjero)")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidarPorRut(@Param("rut") String rut,@Param("idExtranjero") String idExtranjero, Pageable page);

    /**
     * Retorna todas las empresas con sucursales que han sido creadas por un publicador y que aun no han sido validadas que calcen
     * con el nombre entregado como parametro
     *
     * @return {@link crm.entities.Empresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT uue.empresa FROM UsuarioUsmempleoEmpresa uue "+
            "JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
            " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))",
            countQuery = "SELECT DISTINCT COUNT(uue.empresa) FROM UsuarioUsmempleoEmpresa uue "+
                    "JOIN uue.sucursalEmpresa s JOIN uue.empresa e WHERE s.tipoVigencia.codVigencia = 4 AND (UPPER(translate(e.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%')"+
                    " OR UPPER(translate(e.sigla,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombre,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')),'%'))")
    Page<Empresa> buscarEmpresasConSucursalesCreadasPorUsuarioEmpresaUsmempleoPorValidarPorNombre(@Param("nombre") String nombre, Pageable page);


    @Query("SELECT s FROM SucursalEmpresa s WHERE s.empresa.id = :idEmpresa AND UPPER(s.sucSucursal) = 'CASA MATRIZ'")
    SucursalEmpresa traerCasaMatrizEmpresaPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna  un {@Link java.util.Page} con todas las {@Link crm.entities.Empresa} no contactadas.
     *
     * @return Coleccion {@Link.util.Page} de las {@Link crm.entities.Empresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT pe FROM Empresa AS pe "+
            "WHERE pe NOT IN" +
            "(SELECT uue FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN chep.contactoHistoricoEmpresa AS che ) ORDER BY pe.razonSocial",
            countQuery = "SELECT DISTINCT COUNT(pe) FROM Empresa AS pe "+
                    "WHERE pe NOT IN" +
                    "(SELECT uue FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
                    "JOIN chep.contactoHistoricoEmpresa AS che )")
    Page<Empresa> buscarEmpresasNoContactadas(Pageable page);

    /**
     * Retorna  un {@Link java.util.Page} con todas las {@Link crm.entities.Empresa} no contactadas.
     *
     * @param sector Short del sector al que pertenece la empresa.
     *
     * @return Coleccion {@Link.util.Page} de las {@Link crm.entities.Empresa}.
     * @author Felipe Mancilla <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value = "SELECT DISTINCT pe FROM Empresa AS pe "+
            "JOIN pe.sector AS ts " +
            "WHERE pe NOT IN" +
            "(SELECT uue FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN chep.contactoHistoricoEmpresa AS che ) " +
            "AND ts.codigo = :codSector ORDER BY pe.razonSocial",
            countQuery = "SELECT DISTINCT COUNT(pe) FROM Empresa AS pe "+
                    "JOIN pe.sector AS ts " +
                    "WHERE pe NOT IN" +
                    "(SELECT uue FROM ContactoHistoricoEmpresaPersonaParticipante AS chep " +
                    "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
                    "JOIN chep.contactoHistoricoEmpresa AS che ) " +
                    "AND ts.codigo = :codSector ")
    Page<Empresa> buscarEmpresasNoContactadasPorTipoSector(Pageable page, @Param("codSector") Short sector);
}
