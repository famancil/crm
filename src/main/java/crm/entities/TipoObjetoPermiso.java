package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla seguridad.tipo_objeto_permiso.
 * Contiene un listado con los tipos de objetos sobre los cuales se pueden asignar permisos.
 *
 * @author Ignacio Oneto <ignacio.oneto@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_objeto_permiso", schema = "seguridad")
public class TipoObjetoPermiso {

    /*
    * Id del tipo de publicacion
    */
    private Short codigo;

    /*
    * Nombre del objeto que se mostrara en las vistas
    */
    private String nombre;

    /*
        * Nombre del objeto que se utilizara en el sistema
        */
    private String nombreObjeto;

    public TipoObjetoPermiso(){
    }

    @Id
    @Column(name = "cod_objeto_permiso")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_objeto_permiso")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "nom_obj_objeto_permiso")
    public String getNombreObjeto() {
        return nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }
}