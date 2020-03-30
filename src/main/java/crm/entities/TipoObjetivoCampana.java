package crm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad correspondiente a la tabla dbo.tipo_objetivo_campana.
 * Contiene un listado con los tipos de objetivos de las campañas que maneja el sistema
 * <br>
 * <ul>
 *     <li>Sin Información</li>
 *     <li>Recuperados</li>
 *     <li>Nuevo</li>
 *     <li>Recup. - Nuevo</li>
 * </ul>
 *
 * @author Hector Calquin <hector.calquin@alumnos.usm.cl>
 */
@Entity
@Table(name = "tipo_objetivo_campaña", schema = "aexa")
public class TipoObjetivoCampana implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id de la Campana.
     */
    private Short codObjetivoCampana;

    /**
     * Nombre del Objetivo de Campana.
     */
    private String nomObjetivoCampana;

    public TipoObjetivoCampana() {
    }

    public TipoObjetivoCampana(Short codObjetivoCampana) {
        this.codObjetivoCampana = codObjetivoCampana;
    }

    @Id
    @Column(name = "cod_objetivo_campaña")
    public Short getCodObjetivoCampana() {
        return codObjetivoCampana;
    }

    public void setCodObjetivoCampana(Short codObjetivoCampana) {
        this.codObjetivoCampana = codObjetivoCampana;
    }


    @Column(name = "nom_objetivo_campaña")
    public String getNomObjetivoCampana() {
        return nomObjetivoCampana;
    }

    public void setNomObjetivoCampana(String nomObjetivoCampana) {
        this.nomObjetivoCampana = nomObjetivoCampana;
    }



}
