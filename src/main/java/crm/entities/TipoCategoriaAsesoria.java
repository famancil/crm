package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla asesoria.tipo_categoria_asesoria.
 * Contiene los tipos de categoria que puede tener una
 * oferta de asesoria
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_categoria_asesoria", schema="asesoria")
public class TipoCategoriaAsesoria implements Serializable {

    /**
     * Id de la categoria.
     */
    private Short codigo;

    /**
     * Nombre de la categoria.
     */
    private String nombre;

    /**
     * TODO comentar
     */
    private String tipoProyecto;


    public TipoCategoriaAsesoria() {
    }

    public TipoCategoriaAsesoria(Short codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_categoria_asesoria")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_categoria_asesoria")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "tipo_proyecto")
    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }
}
