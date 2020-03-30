package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla academia.tipo_herramienta_informatica
 * Permite tipificar las herramientas informáticas que ingresan los
 * Usuarios.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_herramienta_informatica", schema="academia")
public class TipoHerramientaInformatica {

    /**
     * Identificador del Tipo de Herramienta Informática
     */
    private Short codigo;

    /**
     * Nombre del Tipo de Herramienta Informática
     */
    private String nombre;

    @Id
    @Column(name = "cod_herramienta_informatica")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_herramienta_informatica")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
