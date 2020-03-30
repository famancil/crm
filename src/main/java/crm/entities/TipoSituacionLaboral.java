package crm.entities;

import javax.persistence.*;

/**
 * Entidad correspondiente a la tabla tipo_situacion_laboral. Contiene las distintas
 * situaciones laborales en las que se puede encontrar un usuario. En la version actual del sistema estan disponibles
 * los siguientes valores:
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Estudiante</li>
 *     <li>Estudiante por egresar</li>
 *     <li>Recien Egresado y sin interes en trabajar en el corto plazo</li>
 *     <li>Recien Egresado y en busqueda de nuevas opciones profesionales</li>
 *     <li>Recien Titulado y sin interes en trabajar en el corto plazo</li>
 *     <li>Recien Titulado y en busqueda de nuevas opciones profesionales</li>
 *     <li>Estudiando y trabajando al mismo tiempo</li>
 *     <li>Trabajo como independiente</li>
 *     <li>Trabajo como freelancer</li>
 *     <li>Habitualmente participo en proyectos o asesorias</li>
 *     <li>Actualmente trabajo como profesional con trabajo indefinido</li>
 *     <li>Actualmente trabajo prestando servicios profesionales a honorario</li>
 *     <li>Actualmente con trabajo a plazo fijo</li>
 *     <li>En un cargo temporal por reemplazo de otro profesional</li>
 *     <li>En busqueda de nuevas opciones profesionales</li>
 *     <li>Jubilado o retirado</li>
 * </ul>
 *
 * @author Renata Mella <renata.mella.12@sansano.usm.cl>
 */
@Entity
@Table(name = "tipo_situacion_laboral", schema = "empleo")
public class TipoSituacionLaboral {

    /**
     * Identificador de la instancia
     */
    private Short codigo;

    /**
     * Nombre de la situacion laboral
     */
    private String nombre;

    @Id
    @Column(name = "cod_situacion_laboral")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codSituacionLaboral) {
        this.codigo = codSituacionLaboral;
    }

    @Basic
    @Column(name = "nom_situacion_laboral")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomSituacionLaboral) {
        this.nombre = nomSituacionLaboral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoSituacionLaboral that = (TipoSituacionLaboral) o;

        if (codigo != that.codigo) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) codigo;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}