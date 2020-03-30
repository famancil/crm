package crm.entities;


import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla asesoria.pago_asesoria.
 * Guarda la informacion del pago correspondiente a la suscripcion
 * como asesor por parte de  un Usuario
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "pago_asesoria", schema = "asesoria")
public class PagoAsesoria {

    /**
     * Id del Pago de Asesoria
     */
    private Long id;

    /**
     * Usuario del Pago de Asesoria
     */
    private Usuario usuario;

    /**
     * Fecha de pago del Pago de Asesoria
     */
    private Date fechaPago;

    /**
     * TODO comentar
     */
    private Integer montoPago;

    /**
     * TODO comentar
     */
    private String descripcion;

    /**
     * TODO comentar
     */
    private TipoAporte tipoAporte;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;







    @Id
    @Column(name = "id_pago")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Column(name = "monto_pago")
    public Integer getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Integer montoPago) {
        this.montoPago = montoPago;
    }

    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JoinColumn(name = "cod_aporte", referencedColumnName = "cod_aporte")
    @ManyToOne(fetch = FetchType.LAZY)
    public TipoAporte getTipoAporte() {
        return tipoAporte;
    }

    public void setTipoAporte(TipoAporte tipoAporte) {
        this.tipoAporte = tipoAporte;
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
