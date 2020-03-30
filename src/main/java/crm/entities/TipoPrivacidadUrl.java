package crm.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entidad correspondiente a la tabla public.tipo_privacidad_url
 * Contiene un listado con las privacidades de las url de las páginas ingresadas
 * por los usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_privacidad_url")
public class TipoPrivacidadUrl {

    /*
    * Codigo (Id) del tipo de privacidad url
    */
    private Integer codigo;

    /*
    * Nombre del tipo de privacidad url
    */
    private String nombre;



    @Id
    @Column(name = "cod_privacidad")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_privacidad")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}