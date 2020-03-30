package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.competencia_exalumno
 * Contiene las competencias que han registrado los usuarios.

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "competencia_exalumno")
@IdClass(CompetenciaExalumnoPK.class)
public class CompetenciaExalumno  {

    /**
     * Identificador de la {@link crm.entities.NivelCompetenciaUsmempleo} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private Long nivelCompetenciaUsmempleoId;

    /**
     * Identificador del {@link crm.entities.Usuario} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private Long usuarioId;

    /**
     * {@link crm.entities.NivelCompetenciaUsmempleo} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private NivelCompetenciaUsmempleo nivelCompetenciaUsmempleo;

    /**
     * {@link crm.entities.Usuario} asociada a la {@link crm.entities.CompetenciaExalumno}
     */
    private Usuario usuario;

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
    public Long getNivelCompetenciaUsmempleoId() {
        return nivelCompetenciaUsmempleoId;
    }

    public void setNivelCompetenciaUsmempleoId(Long nivelCompetenciaUsmempleoId) {
        this.nivelCompetenciaUsmempleoId = nivelCompetenciaUsmempleoId;
    }

    @Id
    @Column(name = "usuaex_id")
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @JoinColumn(name = "nivcomusm_id", referencedColumnName = "nivcomusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public NivelCompetenciaUsmempleo getNivelCompetenciaUsmempleo() {
        return nivelCompetenciaUsmempleo;
    }

    public void setNivelCompetenciaUsmempleo(NivelCompetenciaUsmempleo nivelCompetenciaUsmempleo) {
        this.nivelCompetenciaUsmempleo = nivelCompetenciaUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
