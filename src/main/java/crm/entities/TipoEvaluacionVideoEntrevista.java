package crm.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Representa la escala de evaluacion utilizada en el sistema de evaluacion de video entrevistas realizadas entre
 * usuarios exalumnos y usuarios empresa.
 *
 * @author dacuna
 * @since 1.0
 */
@Entity
@Table(name = "tipo_evaluacion_video_entrevista", schema = "video_curriculos_empleos")
public class TipoEvaluacionVideoEntrevista {

    private Short codigo;
    private String nombre;

    public TipoEvaluacionVideoEntrevista() {
    }

    @Id
    @Column(name = "codigo_evaluacion")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    @Column(name = "nombre_evaluacion")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
