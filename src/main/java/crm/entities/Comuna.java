package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla dbo.comuna.
 * Contiene un listado con las comunas de Chile.
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "comuna")
public class Comuna  implements Serializable{

    /**
     * Identificador de la comuna.
     */
    private Short codigo;

    /**
     * {@link crm.entities.Ciudad} a la que pertenece la comuna.
     */
    private Ciudad ciudad;

    /**
     * Nombre de la comuna.
     */
    private String nombre;

    /**
     * Codigo de area de la comuna.
     */
    private Short codigoArea;

    /**
     * Pais a la que pertenece la comuna.
     */
    private Pais pais;

    /**
     * Fecha de ultima modificacion de la instancia actual.
     */
    private Date fechaModificacion;

    /**
     * Rut del usuario que realizo la ultima modificacion.
     */
    private Integer rutUsuario;

    /**
     * Provincia a la que pertenece la comuna
     */
    private Provincia provincia;

    public Comuna() {
    }

    @Id
    @Column(name = "cod_comuna")
    @JsonView(ResponseView.MainView.class)
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_ciudad", referencedColumnName = "cod_ciudad")
    @JsonView(ResponseView.MainView.class)
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Column(name = "nom_comuna")
    @JsonView(ResponseView.MainView.class)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "num_area")
    @JsonView(ResponseView.MainView.class)
    public Short getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(Short codigoArea) {
        this.codigoArea = codigoArea;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(ResponseView.MainView.class)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    @JsonView(ResponseView.MainView.class)
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia")
    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }


}
