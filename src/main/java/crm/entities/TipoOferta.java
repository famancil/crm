package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_publicacion_usmempleo.
 * Contiene un listado con los tipos de publicaciones que maneja el sistema.
 * <br>
 * <ul>
 *  <li>Sin información</li>
 *  <li>Full-Time</li>
 *  <li>Part-Time</li>
 *  <li>Freelance</li>
 *  <li>Práctica</li>
 *  <li>Temporal</li>
 *  <li>Trainee</li>
 *  <li>Beca</li>
 *  <li>Práctica y Memoria</li>
 *  <li>Memoria</li>
 * </ul>
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_oferta", schema = "empleo")
public class TipoOferta {

    /*
    * Id del tipo de publicacion
    */
    private Short codigo;

    /*
    * Nombre del tipo de publicacion
    */
    private String nombre;

    public TipoOferta(){
    }

    @Id
    @Column(name = "cod_oferta")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_oferta")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
