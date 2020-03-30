package crm.entities;

import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entidad correspondiente a la tabla dbo.tipo_estudio.
 * Contiene un listado con los tipos de estudios que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Pregrado</li>
 *     <li>Postdoctorado</li>
 *     <li>Magíster</li>
 *     <li>Doctorado</li>
 *     <li>Licenciatura</li>
 *     <li>Diplomado</li>
 *     <li>Enseñanza Básica</li>
 *     <li>Enseñanza Media</li>
 *     <li>Enseñanza Media Técnico Profesional</li>
 *     <li>Continuación de Estudios</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_estudio", schema = "academia")
public class TipoEstudio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**

     * Id del tido de estudio.
     */
    private Short codEstudio;

    /**
     * Nombre del tipo de estudio.
     */
    private String nomEstudio;


    public TipoEstudio() {
    }

    public TipoEstudio(Short codEstudio) {
        this.codEstudio = codEstudio;
    }


    @Id
    @Column(name = "cod_estudio")
    @JsonView(ResponseView.MainView.class)
    public Short getCodEstudio() {
        return codEstudio;
    }

    public void setCodEstudio(Short codEstudio) {
        this.codEstudio = codEstudio;
    }

    @Column(name = "nom_estudio")
    @JsonView(ResponseView.MainView.class)
    public String getNomEstudio() {
        return nomEstudio;
    }

    public void setNomEstudio(String nomEstudio) {
        this.nomEstudio = nomEstudio;
    }

}
