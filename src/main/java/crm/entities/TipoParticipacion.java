package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla org.tipo_participacion.
 * Contiene los tipos de participacion de un dueño con una empresa.
 *  (Dueño, socio, etc)
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */

@Entity
@Table(name = "tipo_participacion", schema = "org")
public class TipoParticipacion {

    /**
     * Identificador del tipo de participacion
     */
    private Short codigo;

    /**
     * Nombre del tipo de participacion
     */
    private String nombre;


    public TipoParticipacion() {
    }

    public TipoParticipacion(Short codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_participacion")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_participacion")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
