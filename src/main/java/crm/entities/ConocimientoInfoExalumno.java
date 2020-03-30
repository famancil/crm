package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.conocimientos_info_exalumno
 * Guarda los conocimientos de informática que los Usuario declaran
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "conocimientos_info_exalumno")
public class ConocimientoInfoExalumno {

    /**
     * Identificador del conocimiento informatico
     */
    private Long id;

    /**
     * Tipo de la herramienta utilizada
     */
    private TipoHerramientaInformatica tipoHerramientaInformatica;

    /**
     * Usuario al que pertenece el conocimiento
     */
    private Usuario usuario;

    /**
     * Años de experiencia
     */
    private Short nivel;

    /**
     * Años de experiencia
     */
    private String aniosExperiencia;

    /**
     * Ultimo uso de la herramienta informatica
     */
    private String ultimoUso;

    /**
     * Descripción del conocimiento ingresado por el usuario
     */
    private String descripcion;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien modifica en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "coninfexa_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_herramienta_informatica", referencedColumnName = "cod_herramienta_informatica")
    @ManyToOne(optional = false)
    public TipoHerramientaInformatica getTipoHerramientaInformatica() {
        return tipoHerramientaInformatica;
    }

    public void setTipoHerramientaInformatica(TipoHerramientaInformatica tipoHerramientaInformatica) {
        this.tipoHerramientaInformatica = tipoHerramientaInformatica;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "coninfexa_nivel")
    public Short getNivel() {
        return nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    @Column(name = "coninfexa_anos_exp")
    public String getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(String aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Column(name = "coninfexa_ultimo_uso")
    public String getUltimoUso() {
        return ultimoUso;
    }

    public void setUltimoUso(String ultimoUso) {
        this.ultimoUso = ultimoUso;
    }

    @Column(name = "coninfexa_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
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

}
