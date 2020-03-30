package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla public.tipo_test_psicologico.
 * Permite tipificar los diferentes test psicológicos que existen en el mercado
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_test_psicologico")
public class TipoTestPsicologico {

    /*
    * Id del cargo
    */
    private Short codigo;

    /*
    * Nombre del cargo
    */
    private String nombre;



    public TipoTestPsicologico() {
    }

    public TipoTestPsicologico(Short codCargo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_test_psicologico")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nombre_test_psicologico")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
