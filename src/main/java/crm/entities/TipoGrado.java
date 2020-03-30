package crm.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_grado.
 * Contiene un listado con los tipos de grado academicos que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Grado</li>
 *     <li>Licenciado</li>
 *     <li>Magister</li>
 *     <li>Doctorado</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_grado", schema = "academia")
public class TipoGrado {

    /**
     * Id del grado academico.
     */
    private Short codGrado;

    /**
     * Nombre del grado academico.
     */
    private String nomGrado;



    @Id
    @Column(name = "cod_grado")
    public Short getCodGrado() {
        return codGrado;
    }

    public void setCodGrado(Short codGrado) {
        this.codGrado = codGrado;
    }

    @Column(name = "nom_grado")
    public String getNomGrado() {
        return nomGrado;
    }

    public void setNomGrado(String nomGrado) {
        this.nomGrado = nomGrado;
    }
}

