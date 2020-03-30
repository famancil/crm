package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.similitud_profesional_carreras.
 * Guarda la similitud de {@link Carrera} del portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "similitud_profesional_carreras", schema = "empleo")
@IdClass(SimilitudProfesionalCarrerasPK.class)
public class SimilitudProfesionalCarreras {

    /**
     * Identificador de la {@link Carrera} asociada en {@link SimilitudProfesionalCarreras}
     */
    private Long codCarrera;

    /**
     * Identificador de la {@link Institucion} asociada en {@link SimilitudProfesionalCarreras}
     */
    private Short codInstitucion;

    /**
     * Similitud de las carreras
     */
    private String similitud;

    /**
     * Carrera de la relacion CarreraInstitucion
     */
    private Carrera carrera;

    /**
     * Institucion de la relacion CarreraInstitucion
     */
    private Institucion institucion;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;


    /**
     * Rut de quien crea/modifica la institucion en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "cod_carrera")
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @Id
    @Column(name = "cod_institucion")
    public Short getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    @Id
    @Column(name = "similitud")
    public String getSimilitud() {
        return similitud;
    }

    public void setSimilitud(String similitud) {
        this.similitud = similitud;
    }

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonView(ResponseView.MainView.class)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
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
