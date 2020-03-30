package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla asesoria.categoria_asesoria_usuario.
 * Contiene las relaciones entre un usuario y los
 * distintos tipos de categorias de asesorias que
 * existen en la base de datos
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "categoria_asesoria_usuario", schema = "asesoria")
public class CategoriaAsesoriaUsuario {

    /**
     * Id de la oferta asesoria.
     */
    private Long id;

    /**
     * TODO Comentar
     */
    private TipoCategoriaAsesoria tipoCategoriaAsesoria;

    /**
     * TODO Comentar
     */
    private Usuario usuario;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica  en la BD
     */
    private Integer rutUsuario;





    @Id
    @Column(name = "cataseusu_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria_asesoria", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public TipoCategoriaAsesoria getTipoCategoriaAsesoria() {
        return tipoCategoriaAsesoria;
    }

    public void setTipoCategoriaAsesoria(TipoCategoriaAsesoria tipoCategoriaAsesoria) {
        this.tipoCategoriaAsesoria = tipoCategoriaAsesoria;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
