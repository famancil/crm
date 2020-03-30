package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_campana.
 * Contiene un listado con los tipos de campañas que maneja el sistema
 * <br>
 * <ul>
 *     <li>No Especificado</li>
 *     <li>Sin Información</li>
 *     <li>Oculta</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_campaña", schema = "aexa")
public class TipoCampana implements Serializable {

    /**
     * Id de la Campaña
     */
    private Short codigo;

    /**
     * Nombre de la Campaña
     */
    private String nombre;


    public TipoCampana() {
    }

    public TipoCampana(Short codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_campaña")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_campaña")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
