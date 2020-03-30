package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *Contiene todas las aptitudes que puede tener un  Usuario
 *  para efectos de exhibir sus capacidades a la hora
 * de realizar una asesoria
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_aptitud", schema = "asesoria")
public class TipoAptitud  {

    /**
     * Id de la Aptitud
     */
    private Integer codigo;

    /**
     * Nombre de la Aptitud
     */
    private String nombre;


    public TipoAptitud() {
    }

    public TipoAptitud(Integer codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_aptitud")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_aptitud")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
