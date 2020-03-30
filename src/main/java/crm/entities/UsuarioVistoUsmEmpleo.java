package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.usuario_visto_usmempleo.
 * Permite saber cuántas veces una empresa ha visto el CV de un usuario.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "usuario_visto_usmempleo", schema="empleo")
@IdClass(UsuarioVistoUsmEmpleoPK.class)
public class UsuarioVistoUsmEmpleo {

    /**
     * Id del {@link Usuario} que pertenece el CV
     */
    private Long idUsuario;

    /**
     * Identificador del {@link Empresa} que ve el CV
     */
    private Long idEmpresa;

    /**
     * {@link Usuario} que pertenece el CV
     */
    private Usuario usuario;

    /**
     * {@link Empresa} que ve el CV
     */
    private Empresa empresa;

    /**
     * Curriculum visto
     */
    private Boolean vistoCV;

    /**
     * Curriculum descargado
     */
    private Boolean descargadoCV;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica de la institucion en la BD
     */
    private Integer rutUsuario;





    @Id
    @Column(name = "usuaex_id")
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id
    @Column(name = "perempusm_id")
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id", insertable = false, updatable = false)
    @ManyToOne
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Column(name = "usuvisusm_visto_cv")
    public Boolean getVistoCV() {
        return vistoCV;
    }

    public void setVistoCV(Boolean vistoCV) {
        this.vistoCV = vistoCV;
    }

    @Column(name = "usuvisusm_descargado_cv")
    public Boolean getDescargadoCV() {
        return descargadoCV;
    }

    public void setDescargadoCV(Boolean descargadoCV) {
        this.descargadoCV = descargadoCV;
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
