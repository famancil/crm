package crm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla antecedente_colegio
 * Contiene los antecedentes de los colegios en los que ha estado un usuario
 * que se han registrado en la base de datos.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "antecedente_colegio")
public class AntecedenteColegio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * Id del Antecedente Colegio
    */
    private Long id;

    /**
    * Entidad Colegio asociada al antecedente colegio
    */
    private Colegio colegio;

    /**
    * Tipo de Estudio registrado en el antecedente colegio
    */
    private TipoEstudio tipoEstudio;

    /**
    * Usuario asociado al antecedente colegio
    */
    private Usuario usuario;

    /**
    * Año de egreso del usuario, registrado en el antecedente colegio
    */
    private Short anoEgreso;

    /**
    * Año de ingreso del usuario, registrado en el antecedente colegio
    */
    private Short anoIngreso;

    /**
     * Fecha de modificacion del antecedente educacional en la BD
     */
    private Date fechaModificacion;

    /**
     * Titulo en caso de tener.
     */
    private String titulo;

    /**
     * Rut de quien crea/modifica el antecedente educacional en la BD
     */
    private Integer rutUsuario;



    public AntecedenteColegio() {
    }

    public AntecedenteColegio(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "antcol_seq_gen")
    @SequenceGenerator(name = "antcol_seq_gen", sequenceName = "antecedente_colegio_id_seq", allocationSize = 1)
    @Column(name = "antcol_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_estudio", referencedColumnName = "cod_estudio")
    @ManyToOne
    public TipoEstudio getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudio tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    @JoinColumn(name = "cod_colegio", referencedColumnName = "cod_colegio")
    @ManyToOne(optional = false)
    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio ) {
        this.colegio = colegio;
    }


    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "antedu_año_egreso")
    public Short getAnoEgreso() {
        return anoEgreso;
    }

    public void setAnoEgreso(Short anioEgreso) {
        this.anoEgreso = anioEgreso;
    }

    @Column(name = "antedu_año_ingreso")
    public Short getAnoIngreso() {
        return anoIngreso;
    }

    public void setAnoIngreso(Short anteduAnoIngreso) {
        this.anoIngreso = anteduAnoIngreso;
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

    @Column(name = "antedu_titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

}
