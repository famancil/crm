package crm.entities;

import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla contacto_historico_empresa.
 *
 * @author  Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
*/

@Entity
@Table(name = "contacto_historico_empresa", schema = "org")
public class ContactoHistoricoEmpresa {

    /**
     * Identificador de la instancia actual
     */
    private Long id;

    /**
     * Tipo de contacto asociado a la instancia actual
     */
    private TipoContacto tipoContacto;

    /**
     * Tipo de oportunidad asociada a la instancia actual
     */
    private TipoOportunidad tipoOportunidad;

    /**
     * Estado en el que se encuentra la instancia actual
     */
    private TipoEstado tipoEstado;

    /**
     * Nivel de interes que tiene la empresa
     */
    private TipoNivelInteres tipoNivelInteres;

    /**
     * Usuario que realizo el contacto con la empresa
     */
    private Usuario usuario;

    /**
     * Fecha en que se realizo el contacto actual
     */
    private Date fechaContacto;

    /**
     * Fecha proxima en que se realizara un contacto con la empresa
     */
    private Date fechaProximoContacto;

    /**
     * Asunto del contacto actual
     */
    private String asunto;

    /**
     * Acuerdos realizados en la instancia actual
     */
    private String acuerdos;

    /**
     * Rut del usuario que realizo la ultima modificacion en la instancia actual
     */
    private Integer rutUsuario;

    /**
     * Fecha de la ultima modificacion de la instancia actual
     */
    private Date fechaModificacion;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactoHistoricoEmpresa_seq_gen")
    @SequenceGenerator(name = "contactoHistoricoEmpresa_seq_gen", sequenceName = "org.contacto_historico_empresa_conhisemp_id_seq", allocationSize = 1)
    @Column(name = "conhisemp_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_contacto", referencedColumnName = "cod_contacto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    @JoinColumn(name = "cod_oportunidad", referencedColumnName = "cod_oportunidad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public TipoOportunidad getTipoOportunidad() {
        return tipoOportunidad;
    }

    public void setTipoOportunidad(TipoOportunidad tipoOportunidad) {
        this.tipoOportunidad = tipoOportunidad;
    }

    @JoinColumn(name = "cod_estado", referencedColumnName = "cod_estado")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public TipoEstado getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(TipoEstado tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    @JoinColumn(name = "cod_nivel_interes", referencedColumnName = "cod_nivel_interes")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public TipoNivelInteres getTipoNivelInteres() {
        return tipoNivelInteres;
    }

    public void setTipoNivelInteres(TipoNivelInteres tipoNivelInteres) {
        this.tipoNivelInteres = tipoNivelInteres;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "conhisemp_fecha_contacto")
    public Date getFechaContacto() {
        return fechaContacto;
    }

    public void setFechaContacto(Date fechaContacto) {
        this.fechaContacto = fechaContacto;
    }

    @Column(name = "conhisemp_fecha_proximo_contacto")
    public Date getFechaProximoContacto() {
        return fechaProximoContacto;
    }

    public void setFechaProximoContacto(Date fechaProximoContacto) {
        this.fechaProximoContacto = fechaProximoContacto;
    }

    @Column(name = "conhisemp_asunto")
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    @Column(name = "conhisemp_acuerdos")
    public String getAcuerdos() {
        return acuerdos;
    }

    public void setAcuerdos(String acuerdos) {
        this.acuerdos = acuerdos;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @Column(name = "fecha_modificacion")
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}

