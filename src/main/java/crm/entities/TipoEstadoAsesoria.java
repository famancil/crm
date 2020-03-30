package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla asesoria.tipo_estado_asesoria.
 * Contiene los tipos de estados que puede tener una postulación a una asesoría
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_estado_asesoria", schema="asesoria")
public class TipoEstadoAsesoria implements Serializable {

    /**
     * Id de la categoria.
     */
    private Short codigo;

    /**
     * Nombre de la categoria.
     */
    private String nombre;


    public TipoEstadoAsesoria() {
    }

    public TipoEstadoAsesoria(Short codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_estado_asesoria")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_estado_asesoria")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
