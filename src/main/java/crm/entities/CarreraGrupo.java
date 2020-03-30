package crm.entities;



import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.carrera_grupo_usmempleo.
 * Carreras que son administradas en el subgrupo del portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "carrera_grupo_usmempleo", schema = "empleo")
@IdClass(CarreraGrupoPK.class)
public class CarreraGrupo {

    /**
     * Identificador de la {@link Carrera} asociada en {@link CarreraGrupo}
     */
    private Long codCarrera;

    /**
     * Identificador del {@link GrupoEmpleo} asociada en {@link CarreraGrupo}
     */
    private Short idGrupoUsmempleo;

    /**
     * {@link Carrera} asociada a la {@link CarreraGrupo}
     */
    private Carrera carrera;

    /**
     * {@link GrupoEmpleo} asociada a la {@link CarreraGrupo}
     */
    private GrupoEmpleo grupoEmpleo;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;


    public CarreraGrupo() {
    }


    @Id
    @Column(name = "cod_carrera")
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @Id
    @Column(name = "gruusm_id")
    public Short getIdGrupoUsmempleo() {
        return idGrupoUsmempleo;
    }

    public void setIdGrupoUsmempleo(Short idGrupoUsmempleo) {
        this.idGrupoUsmempleo = idGrupoUsmempleo;
    }

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "gruusm_id", referencedColumnName = "gruusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public GrupoEmpleo getGrupoEmpleo() {
        return grupoEmpleo;
    }

    public void setGrupoEmpleo(GrupoEmpleo grupoEmpleo) {
        this.grupoEmpleo = grupoEmpleo;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }
}
