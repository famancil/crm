package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla dbo.carrera.
 * Contiene la informacion acerca de una oferta
 * de asesoria a la cual pueden postular uno o mas
 * Usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "oferta_asesoria", schema = "asesoria")
public class OfertaAsesoria {

    /**
     * Id de la oferta asesoria.
     */
    private Long id;

    /**
     * TODO Comentar
     */
    private UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo;

    /**
     * TODO Comentar
     */
    private TipoCategoriaAsesoria tipoCategoriaAsesoria;

    /**
     * TODO Comentar
     */
    private String titulo;

    /**
     * TODO Comentar
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

    /**
     * TODO Comentar
     */
    private TipoVigencia tipoVigencia;

    /**
     * TODO Comentar
     */
    private Short vacantes;

    /**
     * TODO Comentar
     */
    private String duracion;

    /**
     * TODO Comentar
     */
    private Integer valorAsesoria;

    /**
     * TODO Comentar
     */
    private Boolean mostrarValor;

    /**
     * TODO Comentar
     */
    private String rangoAniosExperiencia;

    /**
     * TODO Comentar
     */
    private Short aniosExperiencia;

    /**
     * TODO Comentar
     */
    private String requisitosMinimos;

    /**
     * TODO Comentar
     */
    private Boolean mostrarPublicador;

    /**
     * TODO Comentar
     */
    private String nombreFantasia;








    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oferta_asesoria_seq_gen")
    @SequenceGenerator(name = "oferta_asesoria_seq_gen", sequenceName = "asesoria.asesoria_asesoria_id_seq", allocationSize = 1)
    @Column(name = "oferta_asesoria_id")
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "usuempusm_id", referencedColumnName = "usuempusm_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public UsuarioEmpresaUsmempleo getUsuarioEmpresaUsmempleo() {
        return usuarioEmpresaUsmempleo;
    }

    public void setUsuarioEmpresaUsmempleo(UsuarioEmpresaUsmempleo usuarioEmpresaUsmempleo) {
        this.usuarioEmpresaUsmempleo = usuarioEmpresaUsmempleo;
    }

    @JoinColumn(name = "cod_categoria_asesoria", referencedColumnName = "cod_categoria_asesoria", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public TipoCategoriaAsesoria getTipoCategoriaAsesoria() {
        return tipoCategoriaAsesoria;
    }

    public void setTipoCategoriaAsesoria(TipoCategoriaAsesoria tipoCategoriaAsesoria) {
        this.tipoCategoriaAsesoria = tipoCategoriaAsesoria;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @Column(name = "vacantes")
    public Short getVacantes() {
        return vacantes;
    }

    public void setVacantes(Short vacantes) {
        this.vacantes = vacantes;
    }

    @Column(name = "duracion")
    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Column(name = "valor_asesoria")
    public Integer getValorAsesoria() {
        return valorAsesoria;
    }

    public void setValorAsesoria(Integer valorAsesoria) {
        this.valorAsesoria = valorAsesoria;
    }

    @Column(name = "mostrar_valor")
    public Boolean getMostrarValor() {
        return mostrarValor;
    }

    public void setMostrarValor(Boolean mostrarValor) {
        this.mostrarValor = mostrarValor;
    }

    @Column(name = "rango_anios_experiencia")
    public String getRangoAniosExperiencia() {
        return rangoAniosExperiencia;
    }

    public void setRangoAniosExperiencia(String rangoAniosExperiencia) {
        this.rangoAniosExperiencia = rangoAniosExperiencia;
    }

    @Column(name = "anios_experiencia")
    public Short getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Short aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Column(name = "requisitos_minimos")
    public String getRequisitosMinimos() {
        return requisitosMinimos;
    }

    public void setRequisitosMinimos(String requisitosMinimos) {
        this.requisitosMinimos = requisitosMinimos;
    }

    @Column(name = "mostrar_publicador")
    public Boolean getMostrarPublicador() {
        return mostrarPublicador;
    }

    public void setMostrarPublicador(Boolean mostrarPublicador) {
        this.mostrarPublicador = mostrarPublicador;
    }

    @Column(name = "nombre_fantasia")
    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    @Column(name = "fecha_modificacion")
    @JsonView(ResponseView.MainView.class)
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
