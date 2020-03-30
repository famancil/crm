package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;

import java.util.Date;

/**
 * Entidad correspondiente a la tabla dbo.carrera.
 * Contiene las carreras registradas en el sistema
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "carrera", schema = "academia")
public class Carrera {

    /**
     * Id de la carrera.
     */
    private Long codCarrera;

    /**
     * Tipo de grado de la carrera.
     */
    private TipoGrado tipoGrado;

    /**
     * Nombre de la carrera.
     */
    private String nombreCarrera;

    /**
     * Abreviacion de la carrera.
     */
    private String abreviacion;

    /**
     * Titulo Carrera.
     */
    private String titulo;

    /**
     * Mencion de la carrera
     */
    private String mencion;

    /**
     * Tipo de vigencia de la carrera
     */
    private TipoVigencia tipoVigencia;

    /**
     * Duracion de la carrera.
     */
    private Integer duracion;

    /**
     * Fecha de creacion de la carrera en la BD
     */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion de la carrera en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica la carrera en la BD
     */
    private Integer rutUsuario;


    public Carrera() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carreras_seq_gen")
    @SequenceGenerator(name = "carreras_seq_gen", sequenceName = "academia.carrera_id_seq", allocationSize = 1)
    @Column(name = "cod_carrera")
    @JsonView(ResponseView.MainView.class)
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @JoinColumn(name = "cod_grado", referencedColumnName = "cod_grado")
    @ManyToOne
    public TipoGrado getTipoGrado() {
        return tipoGrado;
    }

    public void setTipoGrado(TipoGrado tipoGrado) {
        this.tipoGrado = tipoGrado;
    }

    @Column(name = "nombre_carrera")
    @JsonView(ResponseView.MainView.class)
    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    @Column(name = "abreviacion")
    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "mencion")
    public String getMencion() {
        return mencion;
    }

    public void setMencion(String mencion) {
        this.mencion = mencion;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @Column(name = "duracion")
    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
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
    @JsonView(ResponseView.MainView.class)
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
