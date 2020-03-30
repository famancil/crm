package crm.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Entidad correspondiente a la tabla dbo.tipo_estado_credencial. Contiene un listado con los posibles estados que
 * tiene una solicitud de credencial de la red de exalumnos pedida por un {@link Usuario}. En la
 * version actual del sistema los estados posibles corresponden a:
 * <br>
 * <ul>
 *      <li>Sin Información</li>
 *      <li>Solicitada</li>
 *      <li>Generada</li>
 *      <li>Enviada</li>
 *      <li>Recibida</li>
 * </ul>
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tipo_estado_credencial", schema = "aexa")
public class EstadoSolicitudCredencial {

    /**
     * Identificador del estado.
     */
    private Short codigo;

    /**
     * Nombre del estado.
     */
    private String nombre;

    public EstadoSolicitudCredencial() {
    }

    @Id
    @Column(name = "cod_credencial")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codCredencial) {
        this.codigo = codCredencial;
    }

    @Column(name = "nom_credencial")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomCredencial) {
        this.nombre = nomCredencial;
    }


}
