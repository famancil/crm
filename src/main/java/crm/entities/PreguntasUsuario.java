package crm.entities;

import crm.utils.ResponseView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.preguntas_usu_aexa.
 * Tabla en la cual están registradas las preguntas a seleccionar
 * por el usuario en su proceso de registro de su nueva cuenta.
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */

@Entity
@Table(name = "preguntas_usu_aexa")
public class PreguntasUsuario implements Serializable {

    /**
     * Identificador del Tipo de Estado
     */
    private Long codigo;

    /**
     * Nombre del Tipo de Estado
     */
    private String pregunta;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica  en la BD
     */
    private Integer rutUsuario;


    public PreguntasUsuario() {
    }




    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preguntas_usuario_seq_gen")
    @SequenceGenerator(name = "preguntas_usuario_seq_gen", sequenceName = "cod_pregunta_usu_aexa_id_seq", allocationSize = 1)
    @Column(name = "cod_pregunta_usu_aexa")
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Column(name = "pregunta_usu_aexa")
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "rut_usuario")
    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

}