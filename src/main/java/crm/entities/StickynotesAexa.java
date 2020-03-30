package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla aexa.stickynotes_aexa.
 * TODO comentar

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "stickynotes_aexa", schema="aexa")
public class StickynotesAexa {

    /*
    * Id del stickynote
    */
    private Integer id;

    /*
    * Usuario que realiza el Strickynote
    */
    private Usuario usuUsuario;

    /*
    * Usuario a quien va dirigido el Strickynote
    */
    private Usuario usuario;

    /*
    * TODO comentar
    */
    private String recado;

    /*
    * TODO comentar
    */
    public Date fecha;

    /*
    * TODO comentar
    */
    private Boolean estado;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;





    @Id
    @Column(name = "stiaex_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "usu_usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(Usuario usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "stiaex_recado")
    public String getRecado() {
        return recado;
    }

    public void setRecado(String recado) {
        this.recado = recado;
    }

    @Column(name = "stiaex_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "stiaex_estado")
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Column(name = "fecha_modificacion")
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
