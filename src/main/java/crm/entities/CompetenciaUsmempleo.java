package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.competencia_usmempleo
 * Contiene los distintos tipos de competencias que maneja el sistema

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "competencia_usmempleo", schema="empleo")
public class CompetenciaUsmempleo {

    /**
     * Id de la competencia
     */
    private Short id;

    /**
     * Tipo de competencia de la competencia
     */
    private TipoCompetencia tipoCompetencia;

    /**
     * Nombre de la competencia
     */
    private String nombre;

    /**
     * Descripción de la competencia
     */
    private String descripcion;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;




    @Id
    @Column(name = "comusm_id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_competencia_usmempleo", referencedColumnName = "cod_competencia_usmempleo")
    @ManyToOne
    public TipoCompetencia getTipoCompetencia() {
        return tipoCompetencia;
    }

    public void setTipoCompetencia(TipoCompetencia tipoCompetencia) {
        this.tipoCompetencia = tipoCompetencia;
    }

    @Column(name = "comusm_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "comusm_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
