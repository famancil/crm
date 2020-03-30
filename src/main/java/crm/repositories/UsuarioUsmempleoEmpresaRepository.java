package crm.repositories;

import crm.entities.UsuarioUsmempleoEmpresa;
import crm.entities.ContactoHistoricoEmpresa;
import crm.entities.ContactoHistoricoEmpresaFuncionarioParticipante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.UsuarioUsmempleoEmpresa}.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
public interface UsuarioUsmempleoEmpresaRepository extends CrudRepository<UsuarioUsmempleoEmpresa, Long> {

    /**
     * Devuelve una lista de {@link crm.entities.UsuarioUsmempleoEmpresa}
     * que posean un empresa.id igual al parametro otorgado y que tengan codigo de vigencia
     * 1
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.UsuarioUsmempleoEmpresa} que corresponden
     * a contactos con la empresa con id idEmpresa.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue JOIN uue.usuarioEmpresaUsmempleo AS ueu WHERE uue.empresa.id = :idEmpresa AND uue.vigencia.codVigencia = 1 ORDER BY ueu.nombreCompleto ASC")
    List<UsuarioUsmempleoEmpresa> getListaContactosVigentesEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Devuelve una lista de {@link crm.entities.UsuarioUsmempleoEmpresa}
     * que posean un empresa.id igual al parametro otorgado y que no tengan codigo de vigencia
     * 1
     *
     * @param idEmpresa id de empresa a la cual se le requiere obtener el listado de contactos.
     * @return Colleccion {@Link java.util.List} de  {@link crm.entities.UsuarioUsmempleoEmpresa} que corresponden
     * a contactos con la empresa con id idEmpresa que tengan codigo de vigencia distinto de 1.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue JOIN uue.usuarioEmpresaUsmempleo AS ueu WHERE uue.empresa.id = :idEmpresa AND uue.vigencia.codVigencia != 1 ORDER BY ueu.nombreCompleto ASC")
    List<UsuarioUsmempleoEmpresa> getListaContactosNoVigentesEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Retorna un {@link crm.entities.UsuarioUsmempleoEmpresa} que posean un id igual al parametro
     * id.
     *
     * @param id id usado para la busqueda del {@link crm.entities.UsuarioUsmempleoEmpresa}.
     *
     * @return {@link crm.entities.Usuario}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    UsuarioUsmempleoEmpresa findById(Long id);

    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa} que esten asociados a una
     * {@link crm.entities.Empresa} , buscados según el Id de la empresa a la que pertenecen/pertenecieron.
     *
     * @param idEmpresa id de la empresa sobre la cual se desea buscar {@link crm.entities.UsuarioUsmempleoEmpresa}
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue WHERE uue.empresa.id = :idEmpresa AND uue.vigencia.codVigencia = 1 ORDER BY uue.usuarioEmpresaUsmempleo.nombreCompleto ASC")
    List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorIdEmpresa(@Param("idEmpresa") Long idEmpresa);

    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa}, según el calce con el
     * nombre del UsuarioUsmempleoEmpresa pasado como parametro
     *
     * @param nombreUsuarioEmpresaUsmempleo Nombre del usuario desea buscar
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue WHERE UPPER(translate(uue.usuarioEmpresaUsmempleo.nombreCompleto,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(:nombreUsuarioEmpresaUsmempleo) ,'%')  AND uue.vigencia.codVigencia = 1 ORDER BY uue.usuarioEmpresaUsmempleo.nombreCompleto ASC")
    List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorCalceNombreUsuario(@Param("nombreUsuarioEmpresaUsmempleo") String nombreUsuarioEmpresaUsmempleo);

    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa}, que posean una sucursal igual al parametro entregado
     *
     * @param sucursalCodigo id de la sucursal que se quiere usar como parametro de busqueda
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue WHERE uue.sucursalEmpresa.sucursalCodigo = :sucursalCodigo")
    List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorCodSucursalEmpresa(@Param("sucursalCodigo") Long sucursalCodigo);

    /**
     * Obtiene un listado de {@link crm.entities.UsuarioUsmempleoEmpresa}, que posean una cargo igual al parametro entregado
     *
     * @param cargo atributo del cargo que se quiere usar como parametro de busqueda
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM UsuarioUsmempleoEmpresa AS uue WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT(UPPER(:cargo) ,'%')")
    List<UsuarioUsmempleoEmpresa> buscarUsuariosUsmempleoEmpresaPorCargo(@Param("cargo") String cargo);

    /**
     * TODO comentar
     *
     * @param cargo string que pertenece al cargo del empleado.
     * @param nombreFantasiaEmpresa string que pertenece al nombre de la empresa.
     *
     * @return Coleccion ({@link java.util.List}) de {@link crm.entities.UsuarioUsmempleoEmpresa}.
     *
     * @author Felipe Mancilla <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT uue  " +
            "FROM UsuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
            "WHERE ueu.cargo LIKE CONCAT(UPPER(:cargo) ,'%') " +
            "AND pe.nombreFantasiaEmpresa = :nombreFantasiaEmpresa " +
            "AND ueu.nombreCompleto = :nombreEmpleado")
    UsuarioUsmempleoEmpresa buscarEmpleadoPorCargoNombreEmpresaYNombreEmpleado( @Param("cargo") String cargo,
                                                       @Param("nombreFantasiaEmpresa") String nombreFantasiaEmpresa,
                                                                         @Param("nombreEmpleado") String nombreEmpleado);

    /**
     * Retorna todos los empleados con un cargo especifico
     *
     * @param cargo String del cargo del empleado.
     * @param rutEmpresa String del rut al que pertenece la empresa.
     * @param nombreFantasiaEmpresa String del nombre de la empresa.
     *
     * @return {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Felipe Mancilla  <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT uue FROM UsuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "WHERE (UPPER(translate(pe.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "OR UPPER(translate(pe.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')) " +
            "AND pe.rutEmpresa LIKE CONCAT(UPPER(:rut) ,'%') " +
            "AND uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT('%',UPPER(:cargo) ,'%') " +
            "ORDER BY uue.empresa.nombreFantasiaEmpresa ASC",
            countQuery = "SELECT COUNT(uue) FROM UsuarioUsmempleoEmpresa AS uue " +
                    "JOIN uue.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "WHERE (UPPER(translate(pe.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "OR UPPER(translate(pe.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')) " +
                    "AND pe.rutEmpresa LIKE CONCAT(UPPER(:rut) ,'%') " +
                    "AND uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT('%',UPPER(:cargo) ,'%') ")
    Page<UsuarioUsmempleoEmpresa> buscarEmpleadosPorCargoNombreEmpresaYRutEmpresa(Pageable page,@Param("cargo") String cargo, @Param("nombreFantasiaEmpresa") String nombreFantasiaEmpresa
                                                                                  ,@Param("rut") String rutEmpresa);



    /**
     * Retorna todos los empleados con un cargo y sector especifico
     *
     * @param cargo String del cargo del empleado.
     * @param codSector Integer del sector al que pertenece la empresa.
     *
     * @return {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Felipe Mancilla  <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT uue FROM UsuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT(UPPER(:cargo) ,'%') " +
            "AND ts.codigo = :codSector ORDER BY uue.empresa.nombreFantasiaEmpresa ASC",
            countQuery = "SELECT COUNT(uue) FROM UsuarioUsmempleoEmpresa AS uue " +
                    "JOIN uue.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT(UPPER(:cargo) ,'%') " +
                    "AND ts.codigo = :codSector")
    Page<UsuarioUsmempleoEmpresa> buscarEmpleadosPorCargoYSector(Pageable page,@Param("cargo") String cargo,@Param("codSector") Short codSector);

    /**
     * Retorna todos los empleados con un cargo y sector especifico
     *
     * @param cargo String del cargo del empleado.
     * @param codSector Integer del sector al que pertenece la empresa.
     * @param rutEmpresa String del rut al que pertenece la empresa.
     * @param nombreFantasiaEmpresa String del nombre de la empresa.
     *
     * @return {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Felipe Mancilla  <felipe.mancilla@alumnos.usm.cl>
     */
    @Query(value="SELECT uue FROM UsuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.empresa AS pe " +
            "JOIN pe.sector AS ts " +
            "WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT('%',UPPER(:cargo) ,'%') " +
            "AND ts.codigo = :codSector AND pe.rutEmpresa LIKE CONCAT(UPPER(:rut) ,'%') " +
            "AND (UPPER(translate(pe.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
            "OR UPPER(translate(pe.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%')) " +
            "ORDER BY uue.empresa.nombreFantasiaEmpresa ASC",
            countQuery = "SELECT COUNT(uue) FROM UsuarioUsmempleoEmpresa AS uue " +
                    "JOIN uue.empresa AS pe " +
                    "JOIN pe.sector AS ts " +
                    "WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT('%',UPPER(:cargo) ,'%') " +
                    "AND ts.codigo = :codSector AND  pe.rutEmpresa LIKE CONCAT(UPPER(:rut) ,'%') " +
                    "AND UPPER(translate(pe.nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') " +
                    "OR UPPER(translate(pe.razonSocial,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) LIKE CONCAT('%', UPPER(translate(:nombreFantasiaEmpresa,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñÑ', 'aeiouAEIOUaeiouAEIOUNN')) ,'%') ")
    Page<UsuarioUsmempleoEmpresa> buscarEmpleadosPorCargoSectorNombreFantasiaYRutEmpresa(Pageable page,@Param("cargo") String cargo,@Param("codSector") Short codSector,
                                                                 @Param("rut") String rutEmpresa,@Param("nombreFantasiaEmpresa") String nombreFantasiaEmpresa);

    /**
     * Retorna todos los contactos al empleado con un cargo especifico
     *
     * @return {@link crm.entities.UsuarioUsmempleoEmpresa}.
     * @author Felipe Mancilla  <felipe.mancilla@alumnos.usm.cl>
     */
    @Query("SELECT uue FROM ContactoHistoricoEmpresaPersonaParticipante AS chep  " +
            "JOIN chep.usuarioUsmempleoEmpresa AS uue " +
            "JOIN uue.usuarioEmpresaUsmempleo AS ueu " +
            "JOIN chep.contactoHistoricoEmpresa AS che " +
            "WHERE uue.usuarioEmpresaUsmempleo.cargo LIKE CONCAT(UPPER(:cargo) ,'%')")
    List<UsuarioUsmempleoEmpresa> buscarContactosPorCargo(@Param("cargo") String cargo);

}

