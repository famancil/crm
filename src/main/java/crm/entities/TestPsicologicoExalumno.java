package crm.entities;


import com.fasterxml.jackson.annotation.JsonView;
import crm.utils.ResponseView;

import javax.persistence.*;
import java.util.Date;

/**
 * Entidad correspondiente a la tabla public.test_psicologico_exalumno.
 * Guarda los test psicológicos rendidos por los Usuarios
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "test_psicologico_exalumno")
public class TestPsicologicoExalumno {

    /**
     * Id del TestPsicologicoExalumno
     */
    private Integer id;

    /**
     * TODO comentar
     */
    private TipoTestPsicologico tipoTestPsicologico;

    /**
     * Usuario al que pertenece el test
     */
    private Usuario usuario;

    /**
     * TODO comentar
     */
    private String urlArchivo;

    /**
     * TODO comentar
     */
    private Date fecha;

    /**
     * TODO comentar
     */
    private String nombre;

    /**
     * Fecha de modificacion en la BD
     */
    private Date fechaModificacion;

    /**
     * Rut de quien crea/modifica en la BD
     */
    private Integer rutUsuario;





    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_psicologico_exalumno_seq_gen")
    @SequenceGenerator(name = "test_psicologico_exalumno_seq_gen", sequenceName = "test_psicologico_exalumno_tespsiexa_id_seq", allocationSize = 1)
    @Column(name = "tespsiexa_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "cod_test_psicologico", referencedColumnName = "cod_test_psicologico")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public TipoTestPsicologico getTipoTestPsicologico() {
        return tipoTestPsicologico;
    }

    public void setTipoTestPsicologico(TipoTestPsicologico tipoTestPsicologico) {
        this.tipoTestPsicologico = tipoTestPsicologico;
    }

    @JoinColumn(name = "usuaex_id", referencedColumnName = "usuaex_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column(name = "tespsiexa_url_archivo")
    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    @Column(name = "tespsiexa_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "tespsiexa_nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
