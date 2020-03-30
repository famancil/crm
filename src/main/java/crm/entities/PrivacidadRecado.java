package crm.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla aexa.privacidad_recado.
 * Contiene un listado con las privacidades de los {@link crm.entities.RecadoContacto}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "privacidad_recado", schema = "aexa")
public class PrivacidadRecado {

    /*
    * Codigo (Id)  tipo recado
    */
    private Short id;

    /*
    * Nombre del tipo recado
    */
    private String nombre;

    /**
     * TODO comentar
     */
    private Boolean operador;

    /**
     * TODO comentar
     */
    private Boolean publico;

    /**
     * TODO comentar
     */
    private Boolean opAsociado;

    /**
     * Fecha de modificacion de la privacidad del recado en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica de la privacidad del recado en la BD
     */
    private Integer rutUsuario;

    /**
     * {@link crm.entities.RecadoContacto} asociados a la privacidad
     */
    private List<RecadoContacto> recadoContacto;



    public PrivacidadRecado() {
    }

    public PrivacidadRecado(Short id) {
        this.id = id;
    }



    @Id
    @Column(name = "prirec_id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "prirec_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "prirec_operador")
    public Boolean getOperador() {
        return operador;
    }

    public void setOperador(Boolean operador) {
        this.operador = operador;
    }

    @Column(name = "prirec_publico")
    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    @Column(name = "prirec_op_asociado")
    public Boolean getOpAsociado() {
        return opAsociado;
    }

    public void setOpAsociado(Boolean opAsociado) {
        this.opAsociado = opAsociado;
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

    @OneToMany(mappedBy = "privacidadRecado")
    public List<RecadoContacto> getRecadoContacto() {
        return recadoContacto;
    }

    public void setRecadoContacto(List<RecadoContacto> recadoContacto) {
        this.recadoContacto = recadoContacto;
    }
}
