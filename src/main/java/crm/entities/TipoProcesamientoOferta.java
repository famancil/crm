package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla crawler_ofertas.tipo_procesamiento_oferta.
 * TODO comentar
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_procesamiento_oferta", schema = "crawler_ofertas")
public class TipoProcesamientoOferta {

    /*
    * Id del tipo de procesamiento de oferta
    */
    private Integer codigo;

    /*
    * Nombre del tipo de procesamiento de oferta
    */
    private String nombre;


    public TipoProcesamientoOferta() {
    }

    public TipoProcesamientoOferta(Integer codigo) {
        this.codigo = codigo;
    }

    @Id
    @Column(name = "cod_tipo_procesamiento_oferta")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nom_tipo_procesamiento_oferta")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
