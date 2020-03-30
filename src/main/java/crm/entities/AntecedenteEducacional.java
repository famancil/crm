package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.antecedente_educacional.
 * Contiene los antecedentes educacionales que han registrado los usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "antecedente_educacional")
public class AntecedenteEducacional implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * Id del Antecedente Educacional
    */
    private Long id;

    /*
    * Carrera registrada en el antecedente educacional
    */
    private Carrera carrera;

    /*
    * Pais registrado en el antecedente educacional
    */
    private Pais pais;

    /*
    * Tipo de Estudio registrado en el antecedente educacional
    */
    private TipoEstudio tipoEstudio;

    /*
    * Tipo de Estado del Estudio registrado en el antecedente educacional
    */
    private TipoEstadoEstudio tipoEstadoEstudio;

    /*
    * Institucion registrada en el antecedente educacional
    */
    private Institucion institucion;

    /*
    * Usuario asociado al antecedente educacional
    */
    private Usuario usuario;

    /*
    * Año de egreso del usuario, registrado en el antecedente educacional
    */
    private Short anioEgreso;

    /*
    * Año de titulación del usuario, registrado en el antecedente educacional
    */
    private Short anioTitulo;

    /*
    * Año de ingreso del usuario, registrado en el antecedente educacional
    */
    private Short anioIngreso;

    /*
    * Texto resumen del titulo (de que trata memoria)
    */
    private String abstractMemoria;

    /**
     * Fecha de modificacion del antecedente educacional en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica el antecedente educacional en la BD
     */
    private Integer rutUsuario;



    public AntecedenteEducacional() {
    }

    public AntecedenteEducacional(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "antedu_seq_gen")
    @SequenceGenerator(name = "antedu_seq_gen", sequenceName = "antecedente_educacional_seq", allocationSize = 1)
    @Column(name = "antedu_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera")
    @ManyToOne(optional = false)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(optional = false)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @JoinColumn(name = "cod_estudio", referencedColumnName = "cod_estudio")
    @ManyToOne
    public TipoEstudio getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudio tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    @JoinColumn(name = "cod_estado_estudio", referencedColumnName = "cod_estado_estudio")
    @ManyToOne
    public TipoEstadoEstudio getTipoEstadoEstudio() {
        return tipoEstadoEstudio;
    }

    public void setTipoEstadoEstudio(TipoEstadoEstudio tipoEstadoEstudio) {
        this.tipoEstadoEstudio = tipoEstadoEstudio;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion")
    @ManyToOne(optional = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "antedu_año_egreso")
    public Short getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(Short anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    @Column(name = "antedu_año_titulo")
    public Short getAnioTitulo() {
        return anioTitulo;
    }

    public void setAnioTitulo(Short anioTitulo) {
        this.anioTitulo = anioTitulo;
    }

    @Column(name = "antedu_año_ingreso")
    public Short getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Short anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    @Column(name = "antedu_abstract")
    public String getAbstractMemoria() {
        return abstractMemoria;
    }

    public void setAbstractMemoria(String abstractMemoria) {
        this.abstractMemoria = abstractMemoria;
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
