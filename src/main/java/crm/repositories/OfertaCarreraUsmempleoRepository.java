package crm.repositories;

import crm.entities.Carrera;
import crm.entities.OfertaCarreraUsmempleo;
import crm.entities.OfertaCarreraUsmempleoPK;
import crm.entities.OfertaLaboralUsmempleo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para el manejo CRUD de la entidad {@link crm.entities.OfertaCarreraUsmempleo}.
 *
 * @author  Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
public interface OfertaCarreraUsmempleoRepository extends CrudRepository<OfertaCarreraUsmempleo, OfertaCarreraUsmempleoPK> {


    /**
     * Retorna un listado de {@link crm.entities.OfertaCarreraUsmempleo} de la tabla empleo.oferta_carrera_usmempleo
     * según la vigencia, la carrera y el año, especificados como parametro.
     *
     * @param codVigencia codigo de la vigencia de la {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT oc " +
            "FROM OfertaCarreraUsmempleo AS oc " +
            "WHERE oc.carrera.codCarrera = :codCarrera AND oc.ofertaLaboralUsmempleo.vigencia.codVigencia = :codVigencia AND YEAR(oc.ofertaLaboralUsmempleo.fechaInicio) = :año " +
            "ORDER BY oc.ofertaLaboralUsmempleo.fechaPublicacion ASC")
    List<OfertaCarreraUsmempleo> buscarOfertasCarreraUsmempleo( @Param("codCarrera") Long codCarrera,
                                                                @Param("codVigencia") Short codVigencia,
                                                                @Param("año") Integer año );




    /**
     * Obtiene un listado de  {@link crm.entities.OfertaCarreraUsmempleo}, según un id de {@link crm.entities.Carrera}
     *
     * @param codCarrera id de la {@link crm.entities.Carrera} a buscar
     *
     * @return  Lista con {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT oc FROM OfertaCarreraUsmempleo AS oc " +
            "WHERE oc.carrera.codCarrera = :codCarrera ")
    List<OfertaCarreraUsmempleo> buscarPorCodCarrera(@Param("codCarrera") Long codCarrera);




    /**
     * Actualiza la {@link crm.entities.Carrera} asociada de un {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @param idOfertaLaboralBuscar Id de {@link crm.entities.OfertaLaboralUsmempleo} al que se le desea setear el valor
     * @param codCarreraBuscar Id de {@link crm.entities.Carrera} al que se le desea setear el valor
     * @param codCarreraSetear Nuevo Id de {@link crm.entities.Carrera} a registrar
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "UPDATE OfertaCarreraUsmempleo AS oc " +
                    "SET oc.codCarrera = :codCarreraSetear " +
                    "WHERE oc.codCarrera = :codCarreraBuscar " +
                        "AND oc.ofertaLaboralUsmempleoId = :idOfertaLaboralBuscar")
    void actualizarCodCarrera(@Param("codCarreraBuscar") Long codCarreraBuscar, @Param("idOfertaLaboralBuscar") Long idOfertaLaboralBuscar, @Param("codCarreraSetear") Long codCarreraSetear);




    /**
     * Elimina una {@link crm.entities.CarreraInstitucion} segun el id especificados como parametro
     *
     * @param idCarreraBuscar Id de la {@link crm.entities.Carrera}
     * @param IdOfertaLaboralBuscar Id de la {@link crm.entities.OfertaLaboralUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Modifying
    @Query(value = "DELETE FROM OfertaCarreraUsmempleo AS oc " +
                    "WHERE oc.codCarrera = :idCarreraBuscar " +
                        "AND oc.ofertaLaboralUsmempleoId = :IdOfertaLaboralBuscar")
    void eliminar(@Param("idCarreraBuscar") Long idCarreraBuscar, @Param("IdOfertaLaboralBuscar") Long IdOfertaLaboralBuscar);




    /**
     * Retorna un listado de {@link crm.entities.OfertaCarreraUsmempleo} de la tabla empleo.oferta_carrera_usmempleo
     * según una {@link crm.entities.Carrera} especifica
     *
     * @param codCarrera Identificador de la {@link crm.entities.Carrera} que se desea obtener {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @return Coleccion {@Link java.util.List} de {@link crm.entities.OfertaCarreraUsmempleo}
     *
     * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
     */
    @Query("SELECT oc " +
            "FROM OfertaCarreraUsmempleo AS oc " +
            "WHERE oc.carrera.codCarrera = :codCarrera " +
            "ORDER BY oc.ofertaLaboralUsmempleo.fechaPublicacion ASC")
    List<OfertaCarreraUsmempleo> buscarPorCodCarreraOrdenPorFechaPublicacion(@Param("codCarrera") Long codCarrera);

}
