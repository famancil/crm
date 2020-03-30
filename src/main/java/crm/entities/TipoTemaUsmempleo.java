package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla empleo.tipo_tema_usmempleo.
 * Permite tipificar los temas disponibles para los
 * grupos del portal de empleos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_tema_usmempleo", schema = "empleo")
public class TipoTemaUsmempleo {

    /*
    * Id del tema
    */
    private Short codigo;

    /*
    * Nombre del tema
    */
    private String nombre;

    public TipoTemaUsmempleo() {
    }

    @Id
    @Column(name = "cod_tema_usmempleo")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_tema_usmempleo")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
