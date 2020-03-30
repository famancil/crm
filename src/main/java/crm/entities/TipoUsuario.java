package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_usuario_aexa. Contiene un listado con las tipificaciones
 * referentes al estado que posee un {@link Usuario} respecto a un operador del sistema CRM.
 * En la ultima version del sistema, los estados disponibles corresponden a:
 * <br>
 *     <ul>
 *         <li>Antiguo</li>
 *         <li>Nuevo</li>
 *         <li>Validado</li>
 *         <li>Rechazado</li>
 *         <li>Eliminado</li>
 *         <li>Cliente Potencial</li>
 *         <li>Temporal</li>
 *         <li>Exalumno Potencial</li>
 *     </ul>
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tipo_usuario_aexa")
public class TipoUsuario {

    /**
     * Identificador del estado.
     */
    private Short codigo;

    /**
     * Nombre del estado.
     */
    private String nombre;

    public TipoUsuario() {
    }

    @Id
    @Column(name = "cod_usuario_aexa")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codUsuarioAexa) {
        this.codigo = codUsuarioAexa;
    }

    @Column(name = "nom_usuario_aexa")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomUsuarioAexa) {
        this.nombre = nomUsuarioAexa;
    }

}
