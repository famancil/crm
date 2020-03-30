package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.usuario_usmempleo_empresa.
 *
 * @author  Ignacio Oneto <Ignacio.oneto@alumnos.usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "usuario_usmempleo_empresa", schema = "org")
public class UsuarioUsmempleoEmpresa implements Serializable {

    /*
    * Identificador de la entidad actual
    */
    private Long id;

    /*
    * Empresa asociada a la entidad actual
    */
    private Empresa empresa;

    /*
    * Tipo de vigencia de la entidad actual
    */
    private TipoVigencia vigencia;

    /*
    * UsuarioEmpresaUsmempleo asociado a la entidad actual
    */
    private UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo;

    /*
    * Sucursal de la empresa de la entidad actual
    */
    private SucursalEmpresa sucursalEmpresa;

    /*
    * rut del usuario que hizo la ultima modificacion a la entidad actual
    */
    private Integer rutUsuario;

    /*
    * Fecha de la ultima modificacion de la entidad actual
    */
    private Date fechaModificacion;

    /*
    * Fecha de inicio de ver curriculum vitae
    */
    private Date inicioVerCv;

    /*
    * Fecha de termino de ver curriculum vitae
    */
    private Date finVerCv;

    /*
    * Fecha de inicio de ver test
    */
    private Date inicioVerTest;

    /*
    * Fecha de termino de ver test
    */
    private Date finVerTest;

    /*
    * Indica si el perfil esta actualizado o no
    */
    private Boolean perfilActualizado;

    /*
    * Indica la cantidad de contactos a la entidad actual.
    */
    private Integer cantidadContactos;

    public UsuarioUsmempleoEmpresa(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_usmempleo_empresa_seq_gen")
    @SequenceGenerator(name = "usuario_usmempleo_empresa_seq_gen", sequenceName = "org.usuario_usmempleo_empresa_seq", allocationSize = 1)
    @Column(name = "usuempempusm_id")
    @JsonView(ResponseView.MainView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "perempusm_id", referencedColumnName = "perempusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(ResponseView.MainView.class)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(TipoVigencia vigencia) {
        this.vigencia = vigencia;
    }

    @JoinColumn(name = "usuempusm_id", referencedColumnName = "usuempusm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(ResponseView.MainView.class)
    public UsuarioEmpresaUsmempleo getUsuarioEmpresaUsmempleo() {
        return usuarioEmpresaUsmempleo;
    }

    public void setUsuarioEmpresaUsmempleo(UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo) {
        this.usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleo;
    }

    @JoinColumn(name = "suc_codigo", referencedColumnName = "suc_codigo")
    @ManyToOne(fetch = FetchType.LAZY)
    public SucursalEmpresa getSucursalEmpresa() {
        return sucursalEmpresa;
    }

    public void setSucursalEmpresa(SucursalEmpresa sucursalEmpresa) {
        this.sucursalEmpresa = sucursalEmpresa;
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

    @Column(name = "usuusmemp_inicio_ver_cv")
    public Date getInicioVerCv() {
        return inicioVerCv;
    }

    public void setInicioVerCv(Date inicioVerCv) {
        this.inicioVerCv = inicioVerCv;
    }

    @Column(name = "usuusmemp_fin_ver_cv")
    public Date getFinVerCv() {
        return finVerCv;
    }

    public void setFinVerCv(Date finVerCv) {
        this.finVerCv = finVerCv;
    }

    @Column(name = "usuusmemp_inicio_ver_test")
    public Date getInicioVerTest() {
        return inicioVerTest;
    }

    public void setInicioVerTest(Date inicioVerTest) {
        this.inicioVerTest = inicioVerTest;
    }

    @Column(name = "usuusmemp_fin_ver_test")
    public Date getFinVerTest() {
        return finVerTest;
    }

    public void setFinVerTest(Date finVerTest) {
        this.finVerTest = finVerTest;
    }

    @Column(name = "usuempempusm_perfil_actualizado")
    public Boolean getPerfilActualizado() {
        return perfilActualizado;
    }

    public void setPerfilActualizado(Boolean perfilActualizado) {
        this.perfilActualizado = perfilActualizado;
    }

    @Transient
    public Integer getCantidadContactos() {
        return cantidadContactos;
    }

    public void setCantidadContactos(Integer cantidadContactos) {
        this.cantidadContactos = cantidadContactos;
    }
}


