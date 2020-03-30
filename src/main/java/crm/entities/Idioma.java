package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.idioma.
 * Contiene un listado con los idiomas que maneja el sistema

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "idioma")
public class Idioma {

    /*
    * Id del Idioma
    */
    private Short codIdioma;

    /*
    * Nombre del Idioma
    */
    private String nomIdioma;

    public Idioma() {
    }

    public Idioma(Short codIdioma) {
        this.codIdioma = codIdioma;
    }

    @Id
    @Column(name = "cod_idioma")
    public Short getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(Short codIdioma) {
        this.codIdioma = codIdioma;
    }

    @Column(name = "nom_idioma")
    public String getNomIdioma() {
        return nomIdioma;
    }

    public void setNomIdioma(String nomIdioma) {
        this.nomIdioma = nomIdioma;
    }
}
