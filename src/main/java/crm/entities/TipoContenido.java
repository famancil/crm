package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * TODO comentar
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_contenido")
public class TipoContenido  {

    /**
     * Id del Tipo de Contenido
     */
    private Short codigo;

    /**
     * Nombre del Tipo de Contenido
     */
    private String nombre;

    /**
     * TODO comentar
     */
    private String slug;



    public TipoContenido() {
    }

    public TipoContenido(Short codigo) {
        this.codigo = codigo;
    }


    @Id
    @Column(name = "cod_contenido")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "slug")
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
