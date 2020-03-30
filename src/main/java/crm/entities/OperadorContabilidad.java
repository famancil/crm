package crm.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla aexa.operador_contabilidad.
 * Identificar quien es operador de qué usuario en el sistema contabilidad
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */

@Entity
@Table(name = "operador_contabilidad", schema="aexa")
public class OperadorContabilidad implements Serializable {

    /**
     * Identificador del registro
     */
    private Long id;

    /**
     * Vigencia de la relacion entre operador y usuario
     */
    private TipoVigencia tipoVigencia;

    /*
    * Usuario que es manejado por el operador
    */
    private Usuario usuario;

    /*
    * Operador del usuario
    */
    private Usuario usuarioOperador;

    /**
     * Fecha de inicio de la calidad de operador
     */
    private Date fechaInicio;

    /**
     * Fecha de fin de la calidad de operador
     */
    private Date fechaFin;



    public OperadorContabilidad() {
    }




    @Id
    @Column(name = "opecon_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_vigencia", referencedColumnName = "cod_vigencia")
    @ManyToOne
    public TipoVigencia getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(TipoVigencia tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JoinColumn(name = "usuaex_id_operador", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false)
    public Usuario getUsuarioOperador() {
        return usuarioOperador;
    }

    public void setUsuarioOperador(Usuario usuarioOperador) {
        this.usuarioOperador = usuarioOperador;
    }

    @Column(name = "opecon_fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "opecon_fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }











}
