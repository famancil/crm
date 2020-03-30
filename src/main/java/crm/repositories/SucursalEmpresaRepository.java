package crm.repositories;

import crm.entities.Provincia;
import crm.entities.Region;
import crm.entities.SucursalEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.SucursalEmpresa}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface SucursalEmpresaRepository extends CrudRepository<SucursalEmpresa, Long> {

    /**
     * Retorna la {@link crm.entities.SucursalEmpresa} que posea un id igual al parametro otorgado
     *
     * @param idSucursal id de la sucursal que se desea buscar.
     * @return {@link crm.entities.SucursalEmpresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    SucursalEmpresa findBySucursalCodigo(Long idSucursal);

    /**
     * Retorna un listado de {@link crm.entities.SucursalEmpresa} asociadas a la {@link crm.entities.Empresa},
     * que tenga id igual al parametro ingresado
     *
     * @param idEmpresa id de emrpesa a la que se le quieren buscar las sucursales
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.SucursalEmpresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT se FROM SucursalEmpresa se WHERE se.empresa.id = :idEmpresa")
    List<SucursalEmpresa> buscarSucursalesEmpresaByIdEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna la {@link crm.entities.Region} asociada a la {@link crm.entities.Comuna}
     * que tenga un cod_comuna igual al parametro ingresado
     *
     * @param codigo codigo de la comuna perteneciente a la region que se quiere obtener
     * @return {@link crm.entities.Region} de la comuna otorgada por parametro.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT r FROM Comuna c JOIN c.provincia p JOIN p.region r WHERE c.codigo = :codigo")
    Region getRegionComuna(@Param("codigo") Short codigo);

    /**
     * Retorna la cantidad {@Link java.lang.Integer} de contactos activos (que tienen codigo de tipo de vigencia 1)
     * que tiene una {@Link crm.entities.SucursalEmpresa}
     *
     * @param codigo codigo de la sucursal.
     *
     * @return Cantidad {@Link java.lang.Integer} de contactos activos de una sucursal.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(u) FROM UsuarioUsmempleoEmpresa u WHERE u.sucursalEmpresa.sucursalCodigo = :codigo AND u.vigencia.codVigencia = 1")
    Integer getCantidadContactosSucursal(@Param("codigo") Long codigo);

    /**
     * Retorna la cantidad {@Link java.lang.Integer} de ofertas laborales
     * que tiene una {@Link crm.entities.SucursalEmpresa}
     *
     * @param codSucursal codigo de la sucursal.
     * @param idEmpresa id de la empresa dueña de la sucursal.
     *
     * @return Cantidad {@Link java.lang.Integer} de ofertas laborales de una sucursal.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="select count(a) from empleo.admin_oferta_usmempleo a join org.usuario_usmempleo_empresa u on u.usuempempusm_id = a.usuempempusm_id join empleo.oferta_laboral_usmempleo o on o.ofelabusm_id = a.ofelabusm_id WHERE o.perempusm_id = :idEmpresa and u.suc_codigo = :codSucursal", nativeQuery = true)
    Integer getCantidadOfertasSucursal(@Param("idEmpresa") Long idEmpresa, @Param("codSucursal") Long codSucursal);

    /**
     * Retorna la cantidad {@Link java.lang.Integer} de exalumnos
     * que tienen algun tipo de relacion con una {@Link crm.entities.SucursalEmpresa}
     *
     * @param codigo codigo de la sucursal.
     *
     * @return Cantidad {@Link java.lang.Integer} de exalumnos en una sucursal.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(DISTINCT a.usuario.id) from ActividadExalumno a WHERE a.sucursalEmpresa.sucursalCodigo = :codigo")
    Integer getCantidadExalumnosSucursal(@Param("codigo") Long codigo);

    /**
     * Funcion que cuenta las sucursales por tipo de vigencia
     *
     * @return String de la forma vigentes, no_vigentes, por_validar
     * @author Gonzalo Sánchez <gonzalo.sanchezv@alumnos.usm.cl>
     */
    @Query("SELECT COUNT(CASE WHEN s.tipoVigencia.codVigencia = 1 THEN 1 END), " +
            "COUNT(CASE WHEN s.tipoVigencia.codVigencia = 2 THEN 1 END), " +
            "COUNT(CASE WHEN s.tipoVigencia.codVigencia = 4 THEN 1 END) " +
            "FROM SucursalEmpresa s")
    String contarSucursalesPorVigencia();

    /**
     * Retorna un listado de {@link crm.entities.SucursalEmpresa} asociadas a la {@link crm.entities.Empresa} por validar,
     * que tenga id igual al parametro ingresado
     *
     * @param idEmpresa id de emrpesa a la que se le quieren buscar las sucursales
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.SucursalEmpresa}.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT se FROM SucursalEmpresa se WHERE se.empresa.id = :idEmpresa AND se.tipoVigencia = 4")
    List<SucursalEmpresa> buscarSucursalesEmpresaByIdEmpresaPorValidar(@Param("idEmpresa") Long idEmpresa);
}
