package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla academia.colegio.
 * Contiene los colegios registrados en el sistema.
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "colegio", schema = "academia")
public class Colegio implements Serializable {

    /**
     * Codigo del colegio
     */
    private Integer codigo;

    /**
     * Tipo de vigencia que tiene el colegio
     */
    private TipoVigencia vigencia;

    /**
     * Comuna a la que pertenece el colegio
     */
    private Comuna comuna;

    /**
     * Pais al que pertenece el colegio
     */
    private Pais pais;

    /**
     * Nombre oficial del colegio
     */
    private String nombreOficial;

    /**
     * Telefono principal del colegio
     */
    private String fonoPrincipal;

    /**
     * Email del colegio
     */
    private String email;

    /**
     * direccion del colegio
     */
    private String direccion;

    /**
     * Codigo RBD del colegio
     */
    private Integer rbd;

    /**
     * Rut del usuario que realizo la ultima modificacion a la entidad
     */
    private Integer rutUsuario;

    /**
     * Fecha de creacion del registro del colegio
     */
    private Date fechaCreacion;

    /**
     * Nombre del director del colegio
     */
    private Date fechaModificacion;


    public Colegio() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "colegio_seq_gen")
    @SequenceGenerator(name = "colegio_seq_gen", sequenceName = "academia.colegio_id_seq", allocationSize = 1)
    @Column(name = "cod_colegio")
    @JsonView(ResponseView.MainView.class)
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "col_direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String colDireccion) {
        this.direccion = colDireccion;
    }

    @Column(name = "col_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String colEmail) {
        this.email = colEmail;
    }

    @Column(name = "col_fono_principal")
    public String getFonoPrincipal() {
        return fonoPrincipal;
    }

    public void setFonoPrincipal(String colFonoPrincipal) {
        this.fonoPrincipal = colFonoPrincipal;
    }

    @Column(name = "col_nombre_oficial")
    @JsonView(ResponseView.MainView.class)
    public String getNombreOficial() {
        if (nombreOficial == null) {
            return nombreOficial;
        }
        return nombreOficial.replace("\"", "");
    }

    public void setNombreOficial(String colNombreOficial) {
        this.nombreOficial = colNombreOficial;
    }

    @JoinColumn(name = "cod_comuna", referencedColumnName = "cod_comuna")
    @ManyToOne(fetch = FetchType.LAZY)
    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @Column(name = "rbd")
    public Integer getRbd() {
        return rbd;
    }

    public void setRbd(Integer rbd) {
        this.rbd = rbd;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoVigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(TipoVigencia vigencia) {
        this.vigencia = vigencia;
    }

    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Colegio other = (Colegio) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        return hash;
    }
}