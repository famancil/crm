package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.nivel_competencia_usmempleo
 * Contiene los distintos tipos de niveles de las competencia_usmempleo

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "nivel_competencia_usmempleo", schema="empleo")
public class NivelCompetenciaUsmempleo {

    /**
     * Id del Nivel
     */
    private Long id;

    /**
     * TODO comentar
     */
    private CompetenciaUsmempleo competenciaUsmempleo;

    /**
     * Nombre del nivel
     */
    private String nivel;

    /**
     * Descripción del nivel
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
    @Column(name = "nivcomusm_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "comusm_id", referencedColumnName = "comusm_id")
    @ManyToOne
    public CompetenciaUsmempleo getCompetenciaUsmempleo() {
        return competenciaUsmempleo;
    }

    public void setCompetenciaUsmempleo(CompetenciaUsmempleo competenciaUsmempleo) {
        this.competenciaUsmempleo = competenciaUsmempleo;
    }

    @Column(name = "nivcomusm_nivel")
    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Column(name = "nivcomusm_descripcion")
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
