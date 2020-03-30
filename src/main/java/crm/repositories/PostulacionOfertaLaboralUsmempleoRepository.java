package crm.repositories;

import crm.entities.OfertaLaboralUsmempleo;
import crm.entities.PostulacionOfertaLaboralUsmempleo;
import crm.entities.PostulacionOfertaLaboralUsmempleoPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.PostulacionOfertaLaboralUsmempleo}.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface PostulacionOfertaLaboralUsmempleoRepository extends CrudRepository<PostulacionOfertaLaboralUsmempleo, PostulacionOfertaLaboralUsmempleoPK> {

     /**
     * Obtiene un listado por mes de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo}  realizadas
      *
     * @param anio Año sobre el cual se desea obtener los datos.
      *
      * @return  Array de String contenedor de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} por mes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT   COUNT(CASE WHEN MONTH(p.fechaPostulacion)=1 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=2 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=3 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=4 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=5 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=6 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=7 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=8 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=9 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=10 THEN 1 END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=11 THEN 1  END) , " +
                    "COUNT(CASE WHEN MONTH(p.fechaPostulacion)=12 THEN 1  END),  " +
                    "COUNT(1) " +
            "FROM PostulacionOfertaLaboralUsmempleo AS p " +
            "WHERE YEAR(p.fechaPostulacion) = :anio ")
    String indiceCantidadPostulacionesOfertasEmpleoPorMes(@Param("anio") Integer anio);




    /**
     * Obtiene un listado por {@link crm.entities.Region} de la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} realizadas,
     * realizando una busqueda según el estado de vigencia de la {@link crm.entities.OfertaLaboralUsmempleo} y el año
     * de la {@link crm.entities.PostulacionOfertaLaboralUsmempleo}. Las {@link crm.entities.Region} es la registradras en
     * la {@link crm.entities.OfertaLaboralUsmempleo} y no
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las regiones; para cada una de ellos
     *          coleccción {@Link java.util.ArrayList} con el nombre de la region y la cantidad de  {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r.nombre, " +
                    "COUNT( p ) " +
            "FROM PostulacionOfertaLaboralUsmempleo AS p " +
            "JOIN p.ofertaLaboralUsmempleo AS o " +
            "WITH YEAR(p.fechaPostulacion) = :anio " +
            "RIGHT JOIN o.region AS r " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "GROUP BY r.nombre " +
            "ORDER BY r.nombre")
    ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertasEmpleoPorRegion( @Param("codVigencia") String codVigencia,
                                                                                    @Param("anio") Integer anio);




    /**
     * Obtiene un listado de las cantidades de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} a
     * {@link crm.entities.OfertaLaboralUsmempleo}, segmentado por cada {@link crm.entities.TipoOferta},
     * realizando una busqueda según un año y tipoVigencia especificados como parametros.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaLaboralUsmempleo}
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return Coleccion {@Link java.util.ArrayList} contenedora de las {@link crm.entities.TipoOferta}; para cada una de ellas,
     *          se tiene una coleccion {@Link java.util.ArrayList}, contenedora del nombre del {@link crm.entities.TipoOferta}
     *          y la cantidad de {@link crm.entities.PostulacionOfertaLaboralUsmempleo} asociadas.
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT to.nombre, " +
                    "COUNT( p ) " +
            "FROM PostulacionOfertaLaboralUsmempleo AS p " +
            "JOIN p.ofertaLaboralUsmempleo AS o " +
            "WITH YEAR(p.fechaPostulacion) = :anio " +
            "RIGHT JOIN o.oferta AS to " +
            "WITH TO_CHAR(o.vigencia.codVigencia, 'FM9999') LIKE :codVigencia " +
            "GROUP BY to.nombre " +
            "ORDER BY to.nombre")
    ArrayList<ArrayList<String>> indiceCantidadPostulacionesOfertasEmpleoPorTipoOferta( @Param("codVigencia") String codVigencia,
                                                                                        @Param("anio") Integer anio);





    /**
     * Obtiene un listado de la cantidad, para cada rango de edad, de {@link crm.entities.Usuario} que han hecho
     * {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} postulantes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query(value="SELECT COUNT(CASE WHEN (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) < 20 THEN 1 END), " +
                        "COUNT(CASE WHEN (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) >= 20 AND (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) <= 29 THEN 1 END), " +
                        "COUNT(CASE WHEN (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) >= 30 AND (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) <= 39 THEN 1 END), " +
                        "COUNT(CASE WHEN (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) >= 40 AND (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) <= 49 THEN 1 END), " +
                        "COUNT(CASE WHEN (YEAR(CURRENT_DATE) - YEAR(u.fechaNacimiento)) > 50 THEN 1 END), " +
                        "COUNT(u) " +
            "FROM Usuario AS u " +
            "WHERE u IN (SELECT DISTINCT u " +
                        "FROM PostulacionOfertaLaboralUsmempleo AS p " +
                        "JOIN p.usuario AS u " +
                        "WHERE YEAR(p.fechaPostulacion) = :anio ) " )
    String cantidadUsuariosPostulantesPorEdad(@Param("anio") Integer anio);




    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.TipoSituacionLaboral}, de {@link crm.entities.Usuario}
     * que han hecho {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} postulantes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT ts.nombre, " +
            "COUNT (u) " +
            "FROM InfoProfesionalExalumno AS i " +
            "JOIN i.situacionLaboral AS ts " +
            "JOIN i.usuario AS u " +
            "WHERE u IN (SELECT DISTINCT u " +
                        "FROM PostulacionOfertaLaboralUsmempleo AS p " +
                        "JOIN p.usuario AS u " +
                        "WHERE YEAR(p.fechaPostulacion) = :anio ) " +
            "GROUP BY ts.nombre " +
            "ORDER BY ts.nombre")
    ArrayList<ArrayList<String>> cantidadUsuariosPostulantesPorSituacionLaboral(@Param("anio") Integer anio);




    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.Region}, de {@link crm.entities.Usuario}
     * que han hecho {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} postulantes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r.nombre, " +
            "COUNT (u) " +
            "FROM Usuario AS u " +
            "JOIN u.comuna AS c " +
            "JOIN c.provincia AS p " +
            "JOIN p.region AS r " +
            "WHERE u IN (SELECT DISTINCT u " +
                        "FROM PostulacionOfertaLaboralUsmempleo AS p " +
                        "JOIN p.usuario AS u " +
                        "WHERE YEAR(p.fechaPostulacion) = :anio ) " +
            "GROUP BY r.nombre " +
            "ORDER BY r.nombre")
    ArrayList<ArrayList<String>> cantidadUsuariosPostulantesPorRegion(@Param("anio") Integer anio);




    /**
     * Obtiene un listado de la cantidad, para cada {@link crm.entities.Provincia}, de {@link crm.entities.Usuario}
     * que han hecho {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @param anio Año sobre el cual se desea obtener los datos.
     *
     * @return  Array de String contenedor de la cantidad de {@link crm.entities.Usuario} postulantes
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT r.nombre, " +
            "p.nombre, " +
            "COUNT (u) " +
            "FROM Usuario AS u " +
            "JOIN u.comuna AS c " +
            "JOIN c.provincia AS p " +
            "JOIN p.region AS r " +
            "WHERE u IN (SELECT DISTINCT u " +
                        "FROM PostulacionOfertaLaboralUsmempleo AS p " +
                        "JOIN p.usuario AS u " +
                        "WHERE YEAR(p.fechaPostulacion) = :anio ) " +
            "GROUP BY r.nombre, p.nombre " +
            "ORDER BY r.nombre, p.nombre")
    ArrayList<ArrayList<String>> cantidadUsuariosPostulantesPorProvincia(@Param("anio") Integer anio);





    /**
     * Retorna una List con {@link crm.entities.PostulacionOfertaLaboralUsmempleo} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     *
     * @return List con {@link crm.entities.PostulacionOfertaLaboralUsmempleo} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionOfertaLaboralUsmempleo AS p WHERE p.usuarioId = :idUsuario ")
    List<PostulacionOfertaLaboralUsmempleo> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);



    /**
     * Retorna una List con {@link crm.entities.PostulacionOfertaLaboralUsmempleo} que posean un usuarioId
     * igual al parametro que se le entregue
     *
     * @param idUsuario id del {@link crm.entities.Usuario} buscado
     * @param idOfertaLaboralUsmempleo id del {@link crm.entities.OfertaLaboralUsmempleo} buscado
     *
     * @return List con {@link crm.entities.PostulacionOfertaLaboralUsmempleo} asociados a un {@link crm.entities.Usuario}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT p FROM PostulacionOfertaLaboralUsmempleo AS p WHERE p.usuarioId = :idUsuario AND p.ofertaLaboralUsmempleoId = :idOfertaLaboralUsmempleo")
    PostulacionOfertaLaboralUsmempleo buscarPorIdUsuarioYIdOfertaLaboral(@Param("idUsuario") Long idUsuario, @Param("idOfertaLaboralUsmempleo") Long idOfertaLaboralUsmempleo);




    /**
     * Actualiza el {@link crm.entities.Usuario} asociado de un {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @param idOfertaLaboralUsmempleoBuscar Id de {@link crm.entities.OfertaLaboralUsmempleo} al que se le desea setear el valor
     * @param idUsuarioBuscar Id de {@link crm.entities.Usuario} al que se le desea setear el valor
     * @param idUsuarioSetear Nuevo Id a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE PostulacionOfertaLaboralUsmempleo AS p SET p.usuarioId = :idUsuarioSetear WHERE p.ofertaLaboralUsmempleoId = :idOfertaLaboralUsmempleoBuscar AND p.usuarioId = :idUsuarioBuscar")
    void actualizarUsuarioId(@Param("idOfertaLaboralUsmempleoBuscar") Long idOfertaLaboralUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar, @Param("idUsuarioSetear") Long idUsuarioSetear);




    /**
     * Elimina un {@link crm.entities.PostulacionOfertaLaboralUsmempleo} segun los id especificados como parametro
     *
     * @param idOfertaLaboralUsmempleoBuscar Id del {@link crm.entities.OfertaLaboralUsmempleo} asociado a {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulacionOfertaLaboralUsmempleo AS p WHERE p.ofertaLaboralUsmempleoId = :idOfertaLaboralUsmempleoBuscar AND p.usuarioId = :idUsuarioBuscar")
    void
    eliminar(@Param("idOfertaLaboralUsmempleoBuscar") Long idOfertaLaboralUsmempleoBuscar, @Param("idUsuarioBuscar") Long idUsuarioBuscar);

    /**
     * Elimina un {@link crm.entities.PostulacionOfertaLaboralUsmempleo} segun la id  del usuario especificado como parametro
     *
     * @param idUsuarioBuscar Id del {@link crm.entities.Usuario} asociado a {@link crm.entities.PostulacionOfertaLaboralUsmempleo}
     *
     * @author Felipe Mancilla S <felipe.mancilla@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM PostulacionOfertaLaboralUsmempleo AS p WHERE p.usuarioId = :idUsuarioBuscar")
    void eliminarPorIdUsuario(@Param("idUsuarioBuscar") Long idUsuarioBuscar);


}
