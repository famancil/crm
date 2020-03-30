package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.dueño_empresa.
 * Guarda la información de dueños de empresas y su nivel de
 * participación en la empresa (socio, inversionista, etc).
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "dueño_empresa")
@IdClass(DuenoEmpresaPK.class)
public class DuenoEmpresa {

    /**
     * Id del {@link crm.entities.Usuario} que es dueño de la {@link crm.entities.Empresa}
     */
    private Long usuarioId;

    /**
     * Identificador del {@link crm.entities.Empresa} de la cual el {@link crm.entities.Usuario} es Dueño
     */
    private Long empresaId;

    /**
     * {@link crm.entities.Usuario} que es dueño de la {@link crm.entities.Empresa}
     */
    private Usuario usuario;

    /**
     * {@link crm.entities.Empresa} de la cual el {@link crm.entities.Usuario} es Dueño
     */
    private Empresa empresa;

    /**
     * Tipo de participacion (dueño, socio)
     */
    private TipoParticipacion tipoParticipacion;

    /**
     * Vigencia de los datos
     */
    private TipoVigencia tipoVigencia;

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
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "perempusm_id")
    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JoinColumn(name = "cod_participacion", referencedColumnName = "cod_participacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public TipoParticipacion getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(TipoParticipacion tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(optional = false)
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
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
