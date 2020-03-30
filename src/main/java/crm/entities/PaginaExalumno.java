package crm.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.pagina_exalumno
 * Contiene las paginas de los usuarios.

 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "pagina_exalumno")
public class PaginaExalumno {

    /*
    * Id de la pagina ingresada por el usuario
    */
    private Long Id;

    /**
     * Tipo de pagina ingresada por el usuario
     */
    private TipoPagina tipoPagina;

    /**
     * Usuario que ingresada la pagina
     */
    private Usuario usuario;

    /**
     * Dirección url de la pagina ingresada por el usuario
     */
    private String url;

    /**
     * Tipo privacidad de la pagina ingresada por el usuario
     */
    private TipoPrivacidadUrl tipoPrivacidadUrl;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;


    @Id
    @Column(name = "pagexa_id")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @JoinColumn(name = "cod_pagina", referencedColumnName = "cod_pagina")
    @ManyToOne
    public TipoPagina getTipoPagina() {
        return tipoPagina;
    }

    public void setTipoPagina(TipoPagina tipoPagina) {
        this.tipoPagina = tipoPagina;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "pagexa_url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JoinColumn(name = "cod_privacidad", referencedColumnName = "cod_privacidad")
    @ManyToOne
    public TipoPrivacidadUrl getTipoPrivacidadUrl() {
        return tipoPrivacidadUrl;
    }

    public void setTipoPrivacidadUrl(TipoPrivacidadUrl tipoPrivacidadUrl) {
        this.tipoPrivacidadUrl = tipoPrivacidadUrl;
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
