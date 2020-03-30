package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla aexa.tipo_mensaje_recado.
 * Contiene un listado con los tipos de mensajes de los {@link RecadoContacto}
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_mensaje_recado", schema = "aexa")
public class TipoMensajeRecado {

    /*
    * Codigo (Id)  tipo de mensaje del recado
    */
    private Short codigo;

    /*
    * Nombre del tipo de mensaje del recado
    */
    private String nombre;

    public TipoMensajeRecado() {
    }

    public TipoMensajeRecado(Short codigo) {
        this.codigo = codigo;
    }


    @Id
    @Column(name = "cod_mensaje_recado")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_mensaje_recado")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
