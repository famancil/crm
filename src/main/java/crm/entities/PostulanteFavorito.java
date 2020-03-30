package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla empleo.postulante_favorito.
 * Guarda los postulantes favoritos de un perfil publicador
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "postulante_favorito", schema = "empleo")
@IdClass(PostulanteFavoritoPK.class)
public class PostulanteFavorito {


    /**
     * Identificador de la {@link Usuario} asociada en {@link PostulanteFavorito}
     */
    private Long idUsuario;

    /**
     * Identificador de la {@link .UsuarioEmpresaUsmempleo} asociada en {@link PostulanteFavorito}
     */
    private Long idUsuarioEmpresaUsmempleo;

    /**
     * TODO comentar
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;


    /**
     * Rut de quien crea/modifica en la BD
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
    @Column(name = "usuempusm_id")
    public Long getIdUsuarioEmpresaUsmempleo() {
        return idUsuarioEmpresaUsmempleo;
    }

    public void setIdUsuarioEmpresaUsmempleo(Long idUsuarioEmpresaUsmempleo) {
        this.idUsuarioEmpresaUsmempleo = idUsuarioEmpresaUsmempleo;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "usuempusm_id", referencedColumnName = "usuempusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public UsuarioEmpresaUsmempleo getUsuarioEmpresaUsmempleo() {
        return usuarioEmpresaUsmempleo;
    }

    public void setUsuarioEmpresaUsmempleo(UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo) {
        this.usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleo;
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
