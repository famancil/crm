package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_publicacion_usmempleo.
 * Contiene un listado con los tipos de publicaciones que maneja el sistema.
 * <br>
 * <ul>
 *   <li>Prioridad Publicacion</li>
 *   <li>Destacado al Inicio</li>
 *   <li>Destacado y con Prioridad</li>
 *   <li>Sin Destacado y sin Prioridad</li>
 * </ul>
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_publicacion_usmempleo", schema = "empleo")
public class TipoPublicacionUsmempleo {

    /*
    * Id del tipo de publicacion
    */
    private Short codigo;

    /*
    * Nombre del tipo de publicacion
    */
    private String nombre;

    /*
        * Indica si la publicacion es prioritaria
        */
    private Boolean prioridadPublicacion;

    /*
    * Indica si la publicacion sale o no en la pagina de inicio
    */
    private Boolean paginaInicioPublicacion;

    public TipoPublicacionUsmempleo() {
    }

    @Id
    @Column(name = "cod_publicacion")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_publicacion")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "prioridad_publicacion")
    public Boolean getPrioridadPublicacion() {
        return prioridadPublicacion;
    }

    public void setPrioridadPublicacion(Boolean prioridadPublicacion) {
        this.prioridadPublicacion = prioridadPublicacion;
    }

    @Column(name = "pagina_inicio_publicacion")
    public Boolean getPaginaInicioPublicacion() {
        return paginaInicioPublicacion;
    }

    public void setPaginaInicioPublicacion(Boolean paginaInicioPublicacion) {
        this.paginaInicioPublicacion = paginaInicioPublicacion;
    }
}
