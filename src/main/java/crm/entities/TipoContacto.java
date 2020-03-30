package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla org.tipo_contacto.
 * Contiene un listado con los tipos de contactos que se puede hacer con una empresa
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_contacto", schema = "org")
public class TipoContacto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id del Tipo de Contacto
     */
    private Short codContacto;

    /**
     * Nombre del Tipo de Contacto
     */
    private String nomContacto;

    /**
     * Tipo
     * TODO preguntar que cosa es
     */
    private String tipo;


    public TipoContacto() {
    }

    public TipoContacto(Short codContacto) {
        this.codContacto = codContacto;
    }


    @Id
    @Column(name = "cod_contacto")
    public Short getCodContacto() {
        return codContacto;
    }

    public void setCodContacto(Short codContacto) {
        this.codContacto = codContacto;
    }

    @Column(name = "nom_contacto")
    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
