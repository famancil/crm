package crm.repositories;

import crm.entities.InfoProfesionalExalumno;
import crm.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.InfoProfesionalExalumno}.
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
public interface InfoProfesionalExalumnoRepository extends CrudRepository<InfoProfesionalExalumno, Long> {


    /**
     * Retorna un {@link crm.entities.InfoProfesionalExalumno} que posean un usuarioId
     * igual al parametro buscado
     *
     * @param id id usado para la busqueda.
     *
     * @return Instancia de la {@link crm.entities.InfoProfesionalExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    InfoProfesionalExalumno findByUsuarioId(Long id);



    /**
     * Calcula la cantidad de registros de la entidad {@link crm.entities.InfoProfesionalExalumno} para cada
     * {@link crm.entities.TipoSituacionLaboral} que existe dentro de esta tabla (public.info_profesional_exalumno)
     *
     * @return Coleccion {@link java.util.ArrayList} de la cantidad de exalumnos por cada situacion laboral.
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT count(cod_situacion_laboral) from info_profesional_exalumno where cod_situacion_laboral NOTNULL GROUP BY cod_situacion_laboral ORDER BY cod_situacion_laboral", nativeQuery = true)
    ArrayList<Integer> cantidadExalumnosPorSituacionContractual();

    /**
     * Retorna una lista con los codigos de {@link crm.entities.TipoSituacionLaboral} que existen en {@link crm.entities.InfoProfesionalExalumno}
     *
     * @return Coleccion {@link java.util.ArrayList} de los codigos de {@link crm.entities.TipoSituacionLaboral} que existen en {@link crm.entities.InfoProfesionalExalumno}
     * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
     */
    @Query(value="SELECT cod_situacion_laboral from info_profesional_exalumno where cod_situacion_laboral NOTNULL GROUP BY cod_situacion_laboral ORDER BY cod_situacion_laboral", nativeQuery = true)
    ArrayList<Short> codSituacionLaboral();




    /**
     * Obtiene un listado, de manera paginada, de la cantidad de personas segmentado por los años de experiencia de su
     * {@link crm.entities.InfoProfesionalExalumno}, según la {@link crm.entities.Carrera} y la {@link crm.entities.Institucion}
     * especificados como parametros para la busqueda
     *
     * @param codInstitucion Identificador de la {@link crm.entities.Institucion}
     * @param codCarrera Identificador de la {@link crm.entities.Carrera}
     * @param page datos que permiten la paginación.
     *
     * @return Coleccion {@Link java.util.Page} contenedora de las {@link crm.entities.Carrera}; para cada una de ellas
     *          se tiene una coleccion {@Link java.util.ArrayList}, contenedora del nombre de la Carrera y la cantidad de
     *          personas segmentadas por los años de Experiencia
     *          asociados
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT c.nombreCarrera, " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 0 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 1 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 2 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 3 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 4 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 5 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 6 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 7 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 8 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 9 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral = 10 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral > 10 THEN 1 END), " +
                    "COUNT(CASE WHEN ip.anoExpLaboral IS NULL THEN 1 END), " +
                    "COUNT(1) " +
                "FROM InfoProfesionalExalumno AS ip " +
                "JOIN ip.usuario AS u " +
                "JOIN u.antecedenteEducacionalList AS a " +
                "JOIN a.institucion AS i " +
                "JOIN a.carrera AS c " +
                "WHERE i.codInstitucion = :codInstitucion " +
                    "AND TO_CHAR(c.codCarrera, 'FM9999')  LIKE :codCarrera  " +
                "GROUP BY c.nombreCarrera " +
                "ORDER BY c.nombreCarrera",
            countQuery = "SELECT COUNT(c.nombreCarrera) " +
                        "FROM InfoProfesionalExalumno AS ip " +
                        "JOIN ip.usuario AS u " +
                        "JOIN u.antecedenteEducacionalList AS a " +
                        "JOIN a.institucion AS i " +
                        "JOIN a.carrera AS c " +
                        "WHERE i.codInstitucion = :codInstitucion " +
                            "AND TO_CHAR(c.codCarrera, 'FM9999')  LIKE :codCarrera " +
                        "GROUP BY c.nombreCarrera " +
                        "ORDER BY c.nombreCarrera" )
    Page<ArrayList<String>> indiceCantidadPersonasPorAniosExperiencia(@Param("codInstitucion") Short codInstitucion,
                                                                        @Param("codCarrera") String codCarrera ,
                                                                        Pageable page );




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.InfoProfesionalExalumno}
     *
     * @param idBuscar Id del {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE InfoProfesionalExalumno AS i SET i.usuarioId = :idSetear WHERE i.usuarioId = :idBuscar")
    void actualizarUsuarioId(@Param("idBuscar") Long idBuscar, @Param("idSetear") Long idSetear);


    /**
     * Elimina un {@link crm.entities.InfoProfesionalExalumno} segun los id especificados como parametro
     *
     * @param idBuscar Id del {@link crm.entities.InfoProfesionalExalumno}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM InfoProfesionalExalumno AS i WHERE i.usuarioId = :idBuscar")
    void eliminar(@Param("idBuscar") Long idBuscar);


}
