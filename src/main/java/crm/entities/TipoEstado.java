package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla org.tipo_estado.
 * Contiene un listado con los tipos de estados en los que puede estar
 * un contacto (reunion) con una empresa.
 * Permite la tipificación de acciones según:
 * <br>
 * <ul>
 *     <li>Por Realizar</li>
 *     <li>En ejecución</li>
 *     <li>Realizado</li>
 *     <li>Anulado</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */

@Entity
@Table(name = "tipo_estado", schema = "org")
public class TipoEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador del Tipo de Estado
     */
    private Short codEstado;

    /**
     * Nombre del Tipo de Estado
     */
    private String nomEstado;


    public TipoEstado() {
    }

    public TipoEstado(Short codEstado) {
        this.codEstado = codEstado;
    }


    @Id
    @Column(name = "cod_estado")
    public Short getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Short codEstado) {
        this.codEstado = codEstado;
    }

    @Column(name = "nom_estado")
    public String getNomEstado() {
        return nomEstado;
    }

    public void setNomEstado(String nomEstado) {
        this.nomEstado = nomEstado;
    }

}