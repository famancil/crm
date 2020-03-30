package crm.entities;



import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla dbo.carrera_institucion.
 * Relaciona la {@link crm.entities.Carrera} y la
 * {@link crm.entities.Institucion} que la dicta.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "carrera_institucion", schema = "academia")
@IdClass(CarreraInstitucionPK.class)
public class CarreraInstitucion {

    /**
     * Identificador de la {@link crm.entities.Carrera} asociada en {@link crm.entities.CarreraInstitucion}
     */
    private Long codCarrera;

    /**
     * Identificador de la {@link crm.entities.Institucion} asociada en {@link crm.entities.CarreraInstitucion}
     */
    private Short codInstitucion;

    /**
     * Carrera de la relacion CarreraInstitucion
     */
    private Carrera carrera;

    /**
     * Institucion de la relacion CarreraInstitucion
     */
    private Institucion institucion;

    /**
     * Sitio Oficial de la CarreraInstitucion
     */
    private String sitioOficial;

    /**
     * Link de la malla de CarreraInstitucion
     */
    private String linkMalla;

    /**
     * Tipo de vigencia de la carreraInstitucion
    */
    private TipoVigencia tipoVigencia;

    /**
     * Indica si la carreraInstitucion estará elegible en el portal de empleos para las ofertas laborales en la sección de requisitos
     */
    private Boolean habilitadoOfertaLaboral;

    /**
     * Fecha de creacion de la institucion en la BD
     *
    */
    private Date fechaCreacion;

    /**
     * Fecha de modificacion de la institucion en la BD
     */
    private Date fechaModificacion;


    /**
     * Rut de quien crea/modifica la institucion en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "cod_carrera")
    @JsonView(ResponseView.MainView.class)
    public Long getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(Long codCarrera) {
        this.codCarrera = codCarrera;
    }

    @Id
    @Column(name = "cod_institucion")
    public Short getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Short codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    @JoinColumn(name = "cod_carrera", referencedColumnName = "cod_carrera", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonView(ResponseView.MainView.class)
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @JoinColumn(name = "cod_institucion", referencedColumnName = "cod_institucion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    @Column(name = "sitio_oficial")
    public String getSitioOficial() {
        return sitioOficial;
    }

    public void setSitioOficial(String sitioOficial) {
        this.sitioOficial = sitioOficial;
    }

    @Column(name = "link_malla")
    public String getLinkMalla() {
        return linkMalla;
    }

    public void setLinkMalla(String linkMalla) {
        this.linkMalla = linkMalla;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @Column(name = "cod_habilitado_oferta_laboral")
    public Boolean getHabilitadoOfertaLaboral() {
        return habilitadoOfertaLaboral;
    }

    public void setHabilitadoOfertaLaboral(Boolean habilitadoOfertaLaboral) {
        this.habilitadoOfertaLaboral = habilitadoOfertaLaboral;
    }

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
