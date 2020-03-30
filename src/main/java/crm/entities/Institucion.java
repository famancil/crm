package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.institucion.
 * Contiene las instituciones registradas en el sistema
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "institucion", schema = "academia")
public class Institucion {

    public static final Long ID_UTFSM = 25l;

    /**
     * Id de la institucion
     */
    private Short codInstitucion;

    /**
     * Pais de la institucion
     */
    private Pais pais;

    /**
     * Nombre de la institucion
     */
    private String nomInstitucion;

    /**
     * Direccion de la institucion
     */
    private String direccion;

    /**
     * Telefono de la institucion
     */
    private String telefono;

    /**
     * Fecha de creacion de la institucion en la BD
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica de la institucion en la BD
     */
    private Integer rutUsuario;

    /**
     * Tipo de vigencia que tiene una institucion
     */
    private TipoVigencia vigencia;


    public Institucion() {
    }

    public Institucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inst_seq_gen")
    @SequenceGenerator(name = "inst_seq_gen", sequenceName = "academia.institucion_seq", allocationSize = 1)
    @Column(name = "cod_institucion")
    @JsonView(ResponseView.MainView.class)
    public Short getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Column(name = "nom_institucion")
    @JsonView(ResponseView.MainView.class)
    public String getNomInstitucion() {
        return nomInstitucion;
    }

    public void setNomInstitucion(String nomInstitucion) {
        this.nomInstitucion = nomInstitucion;
    }

    @Column(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(TipoVigencia vigencia) {
        this.vigencia = vigencia;
    }
}
