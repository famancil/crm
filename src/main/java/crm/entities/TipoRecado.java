package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla aexa.tipo_recado.
 * Contiene un listado con los tipos de {@link crm.entities.RecadoContacto}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_recado", schema = "aexa")
public class TipoRecado {

    /*
    * Codigo (Id)  tipo recado
    */
    private Short codigo;

    /*
    * Nombre del tipo recado
    */
    private String nombre;

    public TipoRecado() {
    }

    public TipoRecado(Short codigo) {
        this.codigo = codigo;
    }



    @Id
    @Column(name = "cod_recado")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_recado")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
