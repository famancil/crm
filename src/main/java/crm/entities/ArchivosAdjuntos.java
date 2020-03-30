package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.archivos_adjuntos_exalumno.
 * Contiene los archivos adjuntos del usuario en el sistema
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "archivos_adjuntos_exalumno")
public class ArchivosAdjuntos {

    /**
     * Id del archivo adjunto
     */
    private Long id;

    /**
     * {@link crm.entities.Usuario} a la que pertenece el archivo adjunto
     */
    private Usuario usuario;

    /**
     * Dirección url del archivo adjunto
     */
    private String urlArchivo;

    /**
     * Descripción del archivo adjunto
     */
    private String descripcion;

    /**
     * Nombre del archivo adjunto
     */
    private String nombre;

    /**
     * Fecha de modificacion de la carrera en la BD
     */
    private Boolean utilizado;

    /**
     * Rut de quien modifica el archivo adjunto
     */
    private Integer rutUsuario;

    /**
     * Fecha de modificacion del archivo adjunto
     */
    private Date fechaModificacion;




    @Id
    @Column(name = "arcadjexa_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "usuaex_id")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "arcadjexa_url_archivo")
    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    @Column(name = "arcadjexa_descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "arcadjexa_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "arcadjexa_utilizado")
    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }

    @Column(name = "fecha_modificacion")
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
