package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla tipo_como_supo_aexa. Contiene informacion sobre como un usuario
 * conocio la red de exalumnos de su institucion. En la version actual del sistema estan disponibles
 * los siguientes valores:
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>En Relaciones Estudiantiles</li>
 *     <li>En mi departamento o unidad académica</li>
 *     <li>Me conto un compañero de Universidad</li>
 *     <li>Me contactó la oficina de exalumnos via telefónica</li>
 *     <li>En un evento/actividad de la Red de Exalumnos USM</li>
 *     <li>Por un folleto</li>
 *     <li>En mi agrupacion/gremio/asociación de exalumnos</li>
 *     <li>En la TV</li>
 *     <li>Por radio</li>
 *     <li>Portal web - www.exalumnos.usm.cl</li>
 *     <li>Portal - www.sansanos.cl</li>
 *     <li>Canal web - www.tv.sansanos.cl</li>
 *     <li>Revista Digital SANSANIA www.revista.sansanos.cl</li>
 *     <li>Newsletter Sansano - www.newsletter.sansanos.cl</li>
 *     <li>Revista Digital SANSANIA - www.revista.sansanos.cl</li>
 *     <li>Portal de empleos - www.empleos.sansanos.cl</li>
 *     <li>Fotos del Momento - www.zoom.sansanos.cl"</li>
 * </ul>
 *
 * @author  Diego Acuna <diego.acuna@usm.cl>
 * @version 1.0
 * @since   1.0
 */
@Entity
@Table(name = "tipo_como_supo_aexa")
public class ComoSupoDeRedExalumnos {

    /**
     * Codigo correspondiente al tipo referido por titulo.
     */
    private Short codigo;

    /**
     * Titulo descriptivo que refleja una categoria de 'como supo un usuario sobre la red de
     * exalumnos de su institucion'.
     */
    private String titulo;

    public ComoSupoDeRedExalumnos() {
    }

    @Id
    @Column(name = "cod_como_supo_aexa")
    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codComoSupoAexa) {
        this.codigo = codComoSupoAexa;
    }

    @Column(name = "nom_como_supo_aexa")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nomComoSupoAexa) {
        this.titulo = nomComoSupoAexa;
    }

}

